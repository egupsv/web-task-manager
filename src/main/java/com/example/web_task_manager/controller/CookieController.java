package com.example.web_task_manager.controller;

import com.example.web_task_manager.CookieName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieController {
    private static final Logger log = LoggerFactory.getLogger(CookieController.class);
    private static final int COOKIE_MAX_AGE = 7 * 24 * 60 * 60;

    public void createCookie(HttpServletResponse response, CookieName name, String value) {
        String cookieName = name.toString().toLowerCase();
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(COOKIE_MAX_AGE);
        response.addCookie(cookie);
    }

    public String getCookieValue(HttpServletRequest request, CookieName name) {
        String cookieName = name.toString().toLowerCase();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String targetName = cookie.getName();
                if (cookieName.equals(targetName)) {
                    return cookie.getValue();
                }
            }
            return null;
        }
        return null;
    }

    public void eraseCookie(HttpServletResponse response, CookieName name) {
        String cookieName = name.toString().toLowerCase();
        Cookie cookie = new Cookie(cookieName, "");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        log.info("cookie: " + cookieName + " erased");
    }

    public void eraseAllCookies(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null)
            for (Cookie cookie : cookies) {
                cookie.setValue("");
                cookie.setPath(req.getContextPath());
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
    }


}
