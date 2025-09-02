package com.rainy.common.config;


import com.rainy.core.common.Result;
import com.rainy.core.exception.CommonExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Run.Shu
 */
@ControllerAdvice
public class GlobalExceptionHandler extends CommonExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result<Object> globExceptionHandler(Exception exception) {
        return super.exceptionHandler(exception);
    }

}
