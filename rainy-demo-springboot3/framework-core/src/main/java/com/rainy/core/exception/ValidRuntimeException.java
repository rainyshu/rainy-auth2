package com.rainy.core.exception;

public class ValidRuntimeException extends BaseRuntimeException {

    public ValidRuntimeException(String desc) {
        super(CodeBusinessException.B_VALID_ERROR, desc);
    }
}
