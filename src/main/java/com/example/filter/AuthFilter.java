package com.example.filter;

import com.example.common.Constants;
import com.example.common.NoNeedLoginConstants;
import com.example.model.Return;
import com.example.util.NumberUtils;
import com.example.util.SignUtils;
import com.example.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

/**
 * 请求合法性验证
 */
@WebFilter
public class AuthFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 需要登陆
        String uri = URLDecoder.decode(request.getRequestURI(), Constants.ENCODING_UTF8);

        boolean isNeedLoginResult = NoNeedLoginConstants.isNeedLogin(uri);
        LOGGER.info("uri = {}, isNeedLoginResult = {}", uri, isNeedLoginResult);

        String openid = request.getParameter("openid");
        String timestamp = request.getParameter("timestamp");
        String sign = request.getParameter("sign");
        LOGGER.info("openid = {}, timestamp = {}, sing = {}", openid, timestamp, sign);

        if (isNeedLoginResult) {

            // 无openid，尚未登陆
            if (StringUtils.isBlank(openid)) {
                print(response, Return.IS_NOT_LOGIN);
                return;
            }
        }

        // 签名校验
        boolean checkSignResult = SignUtils.checkSign(uri, openid, NumberUtils.toLong(timestamp), sign);
        if (checkSignResult) {
            chain.doFilter(request, response);
            return;
        } else {
            print(response, Return.LOGIN_TIME_OUT);
            return;
        }

    }

    /**
     * 返回响应
     *
     * @param response    响应
     * @param returnValue 返回值
     * @throws IOException
     */
    private void print(HttpServletResponse response, Return returnValue) throws IOException {
        response.setContentType(Constants.CONTEN_TTYPE_JSON);
        response.setCharacterEncoding(Constants.ENCODING_UTF8);
        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(returnValue);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        out.println(jsonString);
        out.flush();
        out.close();
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
