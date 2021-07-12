package com.ekuy.cinema.api.aspect;

import com.ekuy.cinema.api.exception.BusinessException;
import com.ekuy.cinema.api.exception.NoLoginException;
import com.ekuy.cinema.api.exception.RedirectException;
import com.ekuy.cinema.api.model.constant.BusinessCode;
import com.ekuy.cinema.api.model.page.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
public class ExceptionAspect {
    @ExceptionHandler(value = Exception.class)
    public Result ExceptionHandler(Exception e) {
        e.printStackTrace();
        return Result.failure(BusinessCode.SYSTEM_ERROR);
    }

    @ExceptionHandler(value = {NoLoginException.class})
    public Result exceptionHandler(NoLoginException e) {
        e.printStackTrace();
        return new Result(BusinessCode.NEED_LOGIN);
    }

    @ExceptionHandler(value = {BusinessException.class})
    public Result exceptionHandler(BusinessException e) {
        e.printStackTrace();
        return new Result(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = {RedirectException.class})
    public void exceptionHandler(HttpServletResponse response, RedirectException e) throws IOException {
        e.printStackTrace();
        response.sendRedirect(e.getUrl());
    }
}
