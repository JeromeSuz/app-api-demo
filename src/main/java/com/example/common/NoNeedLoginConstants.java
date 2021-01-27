package com.example.common;

import java.util.ArrayList;
import java.util.List;

/**
 * 不需要验证是否需要登陆的接口
 *
 * @author jerome
 * @date 2017/3/22 14:35
 */
public class NoNeedLoginConstants {

    /**
     * 不需要需要验证登录的接口Map
     */
    private static List<String> noNeedLogin = new ArrayList<>();

    static {
        // 不需要验证登录的接口
        // 登陆
        noNeedLogin.add("/login");

    }

    public static boolean isNeedLogin(String uri) {
        return !noNeedLogin.contains(uri);
    }
}
