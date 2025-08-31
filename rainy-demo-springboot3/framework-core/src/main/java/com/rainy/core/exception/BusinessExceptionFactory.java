package com.rainy.core.exception;

/**
 * @author rainy.shu
 */
public class BusinessExceptionFactory {

    public static <T extends BaseRuntimeException> T newInstance(String code, String msg, Class<T> clazz) {
        T exception = null;
        try {
            exception = clazz.newInstance();
            exception.setCode(code);
            exception.setDesc(msg);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return exception;
    }


}
