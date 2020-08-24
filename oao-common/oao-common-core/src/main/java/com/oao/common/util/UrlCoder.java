package com.oao.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlCoder {
    public final static String CHARSET = "UTF-8";

    public static String encode(String s) {
        try {
            return URLEncoder.encode(s, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String decode(String s) {
        try {
            return URLDecoder.decode(s, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
