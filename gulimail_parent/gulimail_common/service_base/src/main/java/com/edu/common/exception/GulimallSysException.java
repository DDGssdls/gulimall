package com.edu.common.exception;

/**
 * @Author: DDG
 * @Date: 2020/5/27 10:53
 * @Description:
 */
public class GulimallSysException extends Exception {
    private String message;

    public GulimallSysException() {
    }

    public GulimallSysException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
