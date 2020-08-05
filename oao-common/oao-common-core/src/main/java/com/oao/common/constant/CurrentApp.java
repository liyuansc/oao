package com.oao.common.constant;

/**
 * Created by liyu on 2020/2/23
 */
public class CurrentApp {
    private static String appId;

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        CurrentApp.appId = appId;
    }
}
