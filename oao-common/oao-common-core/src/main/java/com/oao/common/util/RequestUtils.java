package com.oao.common.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

public class RequestUtils {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = getServletRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getRequest();
    }

    public static HttpServletResponse getResponse() {
        ServletRequestAttributes attributes = getServletRequestAttributes();
        if (attributes == null) {
            return null;
        }
        return attributes.getResponse();
    }

    public static ServletRequestAttributes getServletRequestAttributes() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes;
    }

    public static String getParam(HttpServletRequest request, String paramName, String headerName, String cookieName) {
        String v = request.getParameter(paramName);
        if (v == null) {
            v = request.getHeader(headerName);
        }
        if (v == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                v = Arrays.stream(request.getCookies())
                        .filter(cookie -> cookieName.equals(cookie.getName()))
                        .map(cookie -> cookie.getValue()).findFirst().orElse(null);
            }
        }
        return v;
    }
}
