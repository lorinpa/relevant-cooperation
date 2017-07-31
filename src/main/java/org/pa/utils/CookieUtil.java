/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author mwave
 */
public class CookieUtil {
    
    private static CookieUtil instance;
    
    private static int YEAR_MAX_AGE = 24 * 60 * 60 * 365;
    private static String TOKEN_COOKIE_NAME = "token";

    private CookieUtil() {
    }
    
    public static CookieUtil getInstance() {
        if (instance == null) {
            instance = new CookieUtil();
        }
        return instance;
    }
    
    public Cookie generateCookie(HttpServletRequest request, String value) {
        String serverName= request.getServerName();
        String ctx = request.getContextPath();
        Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, value);
        cookie.setMaxAge(YEAR_MAX_AGE );
        cookie.setDomain(serverName);
        cookie.setPath(ctx);
        cookie.setHttpOnly(true);
        if (request.isSecure()) {
            cookie.setSecure(true);
        }
        return cookie;
    }
    
    public Cookie removeCookie(HttpServletRequest request) {
        String serverName= request.getServerName();
        String ctx = request.getContextPath();
        Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, "");
        cookie.setMaxAge(0);
        cookie.setDomain(serverName);
        cookie.setPath(ctx);
        cookie.setHttpOnly(true);
        if (request.isSecure()) {
            cookie.setSecure(true);
        }
        return cookie;
    }
    
    public String getCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String tokenValue = null;
        Cookie ck = null;
        int len = cookies.length;
        int index;
        for (index=0 ; index <= len; index++) {
            ck = cookies[index++];
            if (ck.getName().equals(TOKEN_COOKIE_NAME)) {
                tokenValue = ck.getValue();
                break;
            }
        }
        return tokenValue;       
    }
}
