package com.ekuy.cinema.api.exception;

import com.ekuy.cinema.api.model.constant.BusinessCode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BusinessException extends RuntimeException {
    private int code;
    public BusinessException(BusinessCode businessCode) {
        super(businessCode.getMsg());
        code = businessCode.getCode();
    }

    public BusinessException(BusinessCode businessCode, Object ...params) {
        super(String.format(businessCode.getMsg(), params));
        code = businessCode.getCode();
    }
}
