package com.ekuy.cinema.api.model.page;

import com.ekuy.cinema.api.model.constant.BusinessCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private int code;
    private T data;
    private String msg;

    public Result(BusinessCode businessCode) {
        code = businessCode.getCode();
        msg = businessCode.getMsg();
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(BusinessCode businessCode, Object... params) {
        code = businessCode.getCode();
        msg = String.format(businessCode.getMsg(), params);
    }

    public Result(BusinessCode businessCode, T data, Object... params) {
        code = businessCode.getCode();
        msg = String.format(businessCode.getMsg(), params);
        this.data = data;
    }

    public Result(BusinessCode businessCode, T data) {
        this(businessCode, data, null);
    }

    public static Result success() {
        return success(null);
    }

    public static Result success(Object data) {
        return new Result(0, data, "");
    }

    public static Result failure(BusinessCode... businessCode) {
        return new Result(businessCode.length > 0 ? businessCode[0] : BusinessCode.SYSTEM_ERROR);
    }
}
