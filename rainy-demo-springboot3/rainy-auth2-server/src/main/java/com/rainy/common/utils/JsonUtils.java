package com.rainy.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 增强版 JSON 工具：
 * 1. boolean 序列化为 1/0
 * 2. 1/0 反序列化为 boolean
 * 3. 常用 API 零侵入
 */
public final class JsonUtils {

    /**
     * 单例 ObjectMapper，线程安全
     */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /* 静态初始化：只做一次配置 */
    static {
        // 忽略未知字段
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许字段名无引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许单引号
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        // 启用源信息显示
        OBJECT_MAPPER.enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature());
        /* 注册 boolean↔int 的转换器 */
        SimpleModule module = new SimpleModule();
        module.addSerializer(Boolean.class, new BooleanToIntSerializer());
        module.addSerializer(boolean.class, new BooleanToIntSerializer());
        OBJECT_MAPPER.registerModule(module);
    }

    /* -------------------------------------------------- 对外 API -------------------------------------------------- */

    public static ObjectMapper getInstance() {
        return OBJECT_MAPPER;
    }

    /* 对象 → JSON 字符串 */
    public static String obj2json(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转 JSON 失败", e);
        }
    }

    /* JSON 字符串 → Java Bean */
    public static <T> T json2pojo(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("JSON 转对象失败", e);
        }
    }

    /* Map → Java Bean */
    public static <T> T map2pojo(Map<?, ?> map, Class<T> clazz) {
        return OBJECT_MAPPER.convertValue(map, clazz);
    }

    /* JSON 字符串 → List<T> */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(
                    json,
                    OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (IOException e) {
            throw new RuntimeException("JSON 转 List 失败", e);
        }
    }

    /* JSON 字符串 → 任意复杂泛型 */
    public static <T> T toGeneric(String json, TypeReference<T> typeRef) {
        try {
            return OBJECT_MAPPER.readValue(json, typeRef);
        } catch (IOException e) {
            throw new RuntimeException("JSON 转复杂泛型失败", e);
        }
    }

    /* Java Bean → Map */
    public static Map<String, Object> bean2map(Object obj) {
        return OBJECT_MAPPER.convertValue(obj, new TypeReference<Map<String, Object>>() {});
    }

    /* -------------------------------------------------- 内部私有类 -------------------------------------------------- */

    /* Boolean → 1/0 的序列化器 */
    private static class BooleanToIntSerializer extends JsonSerializer<Boolean> {
        @Override
        public void serialize(Boolean value, JsonGenerator gen, SerializerProvider serializers)
                throws IOException {
            gen.writeNumber(value ? 1 : 0);
        }
    }

    /* 工具类禁止实例化 */
    private JsonUtils() {}
}