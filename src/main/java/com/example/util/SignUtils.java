package com.example.util;

import com.example.common.Constants;
import com.example.util.cache.CacheFactory;
import com.example.util.cache.CacheI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 验证签名
 * 
 * @author jerome
 * @date 2017/3/22 14:36
 */
public class SignUtils {

    private static CacheI REDIS_CACHE = CacheFactory.getRedisCache();
    private static Logger LOGGER = LoggerFactory.getLogger(SignUtils.class);


    /**
     * 验证请求合法性（签名是否合法）
     *
     * @param uri
     * @param openid
     * @param timestamp
     * @param sign
     * @return
     */
    public static boolean checkSign(String uri, String openid, Long timestamp, String sign) {

        if (Constants.IS_DEBUG == 1) {
            return true;
        }

        // API URL请求过期校验
        long nowTimestamp = System.currentTimeMillis() / 1000;
        long time = nowTimestamp - timestamp;
        if (time >= Constants.REQUEST_TIME_OUT) {
            LOGGER.info("时间戳过期, nowTimestamp = {}, timestamp = {}", nowTimestamp, timestamp);
            return false;
        }

        // 签名的字符串
        String signatureStr = null;
        if (StringUtils.isNotBlank(openid)){
            // 获取token
            String tokenKey = Constants.APP_TOKEN_PREFIX + openid;
            String token = (String) REDIS_CACHE.get(tokenKey);
            LOGGER.info("token is " + token);
            if (StringUtils.isBlank(token)) {
                LOGGER.info("token为空, token is = {}", token);
                return false;
            }

            // 校验签名
            signatureStr = uri + timestamp + token;
        }else{
            signatureStr = uri + timestamp;
        }


        String signResult = MD5Utils.getStringMD5(signatureStr);
        LOGGER.info("signatureStr = {}, signResult = {}, sign = {}", signatureStr, signResult, sign);
        if (signResult.equalsIgnoreCase(sign)) {
            return true;
        }

        return false;
    }
}
