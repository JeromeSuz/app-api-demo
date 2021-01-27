package com.example.controller;

import com.example.common.Constants;
import com.example.common.RedisKeys;
import com.example.model.Return;
import com.example.model.User;
import com.example.service.UserService;
import com.example.util.DateUtils;
import com.example.util.NumberUtils;
import com.example.util.SignUtils;
import com.example.util.StringUtils;
import com.example.util.cache.CacheFactory;
import com.example.util.cache.CacheI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 登录、登出
 *
 * @author jerome
 * @date 2017/2/24 14:38
 */
@RestController
public class LoginController {

    // 密码输错超过次数
    @Value(value = "${login.pwd.error.count}")
    private int pwdErrorCount;

    @Autowired
    private UserService userService;

    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private final CacheI REDIS_CACHE = CacheFactory.getRedisCache();

    /**
     * 登陆
     *
     * @param login_name 登陆名
     * @param pwd        密码（MD5）
     * @return Return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    Return login(@RequestParam("login_name") String login_name, @RequestParam("pwd") String pwd) {

        // 参数值为空判断
        if (StringUtils.isBlank(login_name) || StringUtils.isBlank(pwd)) {
            return Return.PARAM_IS_ERROR;
        }

        // 判断用户是否在规定时间内已输错密码几次， 超过次数今日就不可以输入
        String pwdErrorCountKey = RedisKeys.USER_PWD_ERROR_COUNT_PRE + login_name;
        String pwdErrorCountStr = (String) REDIS_CACHE.get(pwdErrorCountKey);
        int pwdErrorCountValue = 0;
        if (StringUtils.isNotBlank(pwdErrorCountStr)) {
            pwdErrorCountValue = NumberUtils.toInt(REDIS_CACHE.get(pwdErrorCountKey).toString());
            if (pwdErrorCountValue >= pwdErrorCount) {
                LOGGER.warn("密码输入错误达到上限，帐号锁定，login_name = {}", login_name);
                return Return.ACCOUNT_LOCK;
            }
        }

        // 查询用户表信息，并校验密码
        User user = userService.findUserByLoginName(login_name);
        if (user == null) {
            return Return.USER_ERROR;
        }

        String token = UUID.randomUUID().toString().replace("-", "");
        // 返回的用户信息
        Map<String, Object> returnUser = new HashMap<>();
        returnUser.put("token", token);
        returnUser.put("openid", user.getOpenid());
        returnUser.put("head_pic_key", "E99A18C428CB38D5F260853678922E03");

        // 登陆成功处理
        if (pwd.toLowerCase().equals(user.getPwd().toLowerCase())) {

            // 删除旧的token
            String token_key = Constants.APP_TOKEN_PREFIX + user.getOpenid();
            String old_token = (String) REDIS_CACHE.get(token_key);
            LOGGER.info("old_token is = {}", old_token);
            if (StringUtils.isNotBlank(old_token)) {
                REDIS_CACHE.delete(old_token);
            }

            // 生成新token,存入redis
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(user);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            // 根据Token缓存用户信息
            if (REDIS_CACHE.put(token, jsonString, Constants.APP_TOKEN_TIME_OUT)) {
                // 缓存用户Token信息
                REDIS_CACHE.put(token_key, token, Constants.APP_TOKEN_TIME_OUT);
            }

            // XXX 将登陆日志丢往队列处理
            /*
            Map<String, Object> logMap = new HashMap<String, Object>();
            logMap.clear();
            logMap.put("head", requestHead);
            logMap.put("user", user);
            logMap.put("ip", ServletUtils.getRemortIP(request));
            logMap.put("visit_time", SDF.format(new Date()));
            long rpushResult = REDIS_CACHE.rpush(Config.LOG_SERVER_REDIS_QUEUE, GSON.toJson(logMap));
            LOG.info("rpushResult is " + rpushResult);
            */

            // XXX 抢登,如果已经在其他地方登陆过,则需要推送旧客户端处理下线,并通知用户

        } else {
            // 密码错误
            // 今日内有输错时，缓存记录输错次数，达到次数锁定账号至0点
            REDIS_CACHE.put(pwdErrorCountKey, ++pwdErrorCountValue, DateUtils.getTodaySurplusSeconds());
            if (pwdErrorCountValue >= pwdErrorCount) {
                LOGGER.warn("密码输入错误达到上限，帐号锁定，login_name = {}", login_name);
                return Return.ACCOUNT_LOCK;
            }

            return Return.PWD_ERROR;
        }

        return new Return(-20002, "登陆成功", returnUser);
    }

    /**
     * 登出
     *
     * @param openid
     * @param timestamp
     * @param sign
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    Return logout(HttpServletRequest request, @RequestParam("openid") String openid,
                  @RequestParam("timestamp") long timestamp, @RequestParam("sign") String sign) {

        // 参数校验
        if (StringUtils.isBlank(openid) || StringUtils.isBlank(sign) || timestamp == 0) {
            return Return.PARAM_IS_ERROR;
        }

        String url = request.getRequestURI();
        if (SignUtils.checkSign(url, openid, timestamp, sign)) {
            // 删除缓存的Token和用户信息
            String userTokenCache = Constants.APP_TOKEN_PREFIX + openid;
            String userCache = (String) REDIS_CACHE.get(userTokenCache);
            boolean deleteUserTokenResult = REDIS_CACHE.delete(userTokenCache);
            boolean deleteUserResult = REDIS_CACHE.delete(userCache);
            LOGGER.info("openid = {}, delete token result = {}, delete token_key result = {}",
                    openid, deleteUserTokenResult, deleteUserResult);
        }

        return Return.LOGOUT_SUCCESS;
    }


}
