package com.rainy.core.exception;

import com.rainy.core.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rainy.shu
 */
public class CommonExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CommonExceptionHandler.class);

    public Result<Object> exceptionHandler(Exception exception) {
        Result<Object> resultVo = new Result<Object>();
        if (exception instanceof ValidRuntimeException) {
            resultVo.setCode(CodeBusinessException.B_VALID_ERROR);
            resultVo.setMsg(exception.getMessage());
            resultVo.setData(null);
        } else if (exception instanceof BaseRuntimeException baseRuntimeException) {
            resultVo.setCode(baseRuntimeException.getCode());
            resultVo.setMsg(baseRuntimeException.getDesc());
            resultVo.setData(null);
        } else {
            resultVo.setCode(CodeBusinessException.B_UNKNOWN_ERROR);
            resultVo.setMsg("程序错误");
            resultVo.setData(null);
        }
        logger.warn("common exception, message: ", exception);
        return resultVo;
    }

}

