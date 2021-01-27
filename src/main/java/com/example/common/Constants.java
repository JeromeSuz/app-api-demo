package com.example.common;


import com.example.util.GetCfgUtils;
import com.example.util.NumberUtils;

/**
 * 常量
 *
 * @author jerome
 * @date 2017/2/22 14:19
 */
public class Constants {

    public static final String CONTEN_TTYPE_JSON = "application/json;charset=UTF-8";
    public static final String ENCODING_UTF8 = "UTF-8";

    /** 是否调试 0：否，1：是 */
    public static final int IS_DEBUG = NumberUtils.toInt(GetCfgUtils.getDefaultValue("is.debug","0"));
    /** API URL请求过期时间(s) */
    public static final int REQUEST_TIME_OUT = NumberUtils.toInt(GetCfgUtils.getDefaultValue("request.time.out","1800"));

    /** APP Token 前缀 */
    public static final String APP_TOKEN_PREFIX = "app_token_";
    /** APP Token 过期时间 */
    public static final int APP_TOKEN_TIME_OUT = 60 * 60 * 24 * 365;


}
