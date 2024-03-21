package org.monkey.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

public class JsonUtil {

    /**
     * 对象转换为json字符串
     *
     * @param obj 需要转换的对象
     * @return json字符串
     * @param <T> obj类型
     */
    public static <T> String objToStr(T obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println("json 转换失败");
            return null;
        }
    }

    /**
     * json字符串转换为对象
     *
     * @param str 待转化的json字符串
     * @param clazz 目标对象的Class对象
     * @return 结果对象
     * @param <T> T
     */
    public static <T> T strToObj(String str, Class<T> clazz) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(str, clazz);
        } catch (JsonMappingException e) {
            System.out.println("JsonMappingException: json 转换失败");
        } catch (JsonProcessingException e) {
            System.out.println("JsonProcessingException: json 转换失败");
        }
        return null;
    }
}
