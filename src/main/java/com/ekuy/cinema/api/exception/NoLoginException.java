package com.ekuy.cinema.api.exception;


import com.ekuy.cinema.api.model.constant.BusinessCode;

public class NoLoginException extends RuntimeException {
    private int code;
    public NoLoginException() {
        super(BusinessCode.NEED_LOGIN.getMsg());
        code = BusinessCode.NEED_LOGIN.getCode();
    }
}
