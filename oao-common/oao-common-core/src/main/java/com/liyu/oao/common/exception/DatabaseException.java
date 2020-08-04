package com.liyu.oao.common.exception;

/**
 * Created by liyu on 2017/6/14.
 */
public class DatabaseException extends RuntimeException {
    public DatabaseException() {
        super();
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

    public DatabaseException(Exception e) {
        super(e.getMessage(), e);
    }
}
