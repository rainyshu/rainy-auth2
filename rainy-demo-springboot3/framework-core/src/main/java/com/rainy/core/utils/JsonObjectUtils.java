package com.rainy.core.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description json转换
 * @Author rainy.shu
 * @Version
 **/
public class JsonObjectUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 将对象转换成json字符串
     * @param obj
     * @return
     */
    public static String objectToJson(Object obj) {
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * json字符串转换对象
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T jsonToObject(String json, Class<T> clazz){
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private JsonObjectUtils() {

    }

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true)
            .setSerializationInclusion(JsonInclude.Include.ALWAYS);

    public static <T> String objToStr(T value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T objToObj(Object object, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(object, clazz);
    }

    public static <T> List<T> objToList(Object object, Class<T> clazz) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, clazz);
            return OBJECT_MAPPER.readValue(objToStr(object), javaType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
