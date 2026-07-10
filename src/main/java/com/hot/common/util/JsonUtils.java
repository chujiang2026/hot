package com.hot.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON 工具类：基于 Jackson，提供对象/Map 与 JSON 字符串的互转。
 * 静态方法，任意类无需注入即可使用。
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 反序列化时忽略 JSON 中存在但 Java 对象没有的字段，提升兼容性
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtils() {
    }

    /** 获取共享的 ObjectMapper（需要更细粒度操作时使用）。 */
    public static ObjectMapper mapper() {
        return MAPPER;
    }

    /** 对象 / Map 转 JSON 字符串。 */
    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转JSON失败", e);
        }
    }

    /** JSON 字符串转指定类型对象。 */
    public static <T> T parse(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON转对象失败", e);
        }
    }

    /** JSON 字符串转泛型对象，如 {@code parse(json, new TypeReference<Map<String,Object>>(){})}。 */
    public static <T> T parse(String json, TypeReference<T> typeRef) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON转对象失败", e);
        }
    }

    /** JSON 字符串转 JsonNode（树模型），便于按路径取值。 */
    public static JsonNode parseTree(String json) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        try {
            return MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON解析失败", e);
        }
    }
}
