package com.ekuy.cinema.api.model.constant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BusinessCode {
    NEED_LOGIN(-1, "登陆已过期，请重新登陆..."),
    SYSTEM_ERROR(500, "系统开小差啦，请稍后重试..."),
    ERROR_GET_MP(501, "小程序信息获取失败，请稍后重试...");

    private int code;
    private String msg;
}
