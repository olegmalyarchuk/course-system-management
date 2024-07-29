package com.coursesystem.util;

import com.coursesystem.configuration.exception.ErrorCode;
import com.coursesystem.configuration.exception.SystemException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {
    public static final ObjectMapper objectMapperWithTimestampDateFormat = new ObjectMapper();

    public JsonUtil() {
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        } else {
            try {
                return objectMapperWithTimestampDateFormat.readValue(json, clazz);
            } catch (IOException var3) {
                throw new SystemException("Cannot deserialize", ErrorCode.BAD_REQUEST);
            }
        }
    }

    public static <T> T deserialize(String json, TypeReference<T> type) {
        if (json == null) {
            return null;
        } else {
            try {
                return objectMapperWithTimestampDateFormat.readValue(json, type);
            } catch (IOException var3) {
                throw new SystemException("Cannot deserialize", ErrorCode.BAD_REQUEST);
            }
        }
    }

    public static String serialize(Object object) {
        if (object == null) {
            return null;
        } else {
            try {
                return objectMapperWithTimestampDateFormat.writeValueAsString(object);
            } catch (JsonProcessingException var2) {
                throw new SystemException("Cannot serialize: ", ErrorCode.BAD_REQUEST);
            }
        }
    }

    public static void hasAllFields(Class<?> target, String[] fields) {
        if (fields != null && fields.length != 0) {
            List<String> missingFields = new ArrayList();
            String[] var3 = fields;
            int var4 = fields.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String field = var3[var5];
                if (ReflectionUtils.findField(target, field) == null) {
                    missingFields.add(field);
                }
            }

            if (missingFields.size() != 0) {
                String errorFields = (String) missingFields.stream().collect(Collectors.joining(",", "[", "]"));
                throw new SystemException("Following fields don't exist: " + errorFields, ErrorCode.BAD_REQUEST);
            }
        }
    }

    static {
        objectMapperWithTimestampDateFormat.registerModule(new JavaTimeModule());
        objectMapperWithTimestampDateFormat.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapperWithTimestampDateFormat.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        objectMapperWithTimestampDateFormat.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule simpleModule = new SimpleModule();
        objectMapperWithTimestampDateFormat.registerModule(simpleModule);
    }
}
