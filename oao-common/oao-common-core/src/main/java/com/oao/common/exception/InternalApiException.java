package com.oao.common.exception;

/**
 * Created by liyu on 2017/6/14.
 */
public class InternalApiException extends RuntimeException {
    private String appId;

    public InternalApiException(String appId) {
        super();
        this.appId = appId;
    }

    public InternalApiException(String appId, String message) {
        super(message);
        this.appId = appId;
    }

    public InternalApiException(String appId, String message, Throwable cause) {
        super(message, cause);
        this.appId = appId;
    }

    public InternalApiException(String appId, Throwable cause) {
        super(cause);
        this.appId = appId;
    }

    public InternalApiException(String appId, Exception e) {
        super(e.getMessage(), e);
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }
}
