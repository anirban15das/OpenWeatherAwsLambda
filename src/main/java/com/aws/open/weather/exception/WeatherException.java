package com.aws.open.weather.exception;

import lombok.Getter;

@Getter
public class WeatherException extends RuntimeException {

    private final String  rootErrorMsg;
    private final String msg;
    private final Exception cause;
    public WeatherException(String msg, Exception cause) {
        this.cause = cause;
        this.msg=msg;
        this.rootErrorMsg = cause.getMessage();
    }
}
