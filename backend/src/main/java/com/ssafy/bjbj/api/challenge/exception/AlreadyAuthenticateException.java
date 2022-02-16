package com.ssafy.bjbj.api.challenge.exception;

public class AlreadyAuthenticateException extends RuntimeException {
    public AlreadyAuthenticateException(String message) {
        super(message);
    }
}
