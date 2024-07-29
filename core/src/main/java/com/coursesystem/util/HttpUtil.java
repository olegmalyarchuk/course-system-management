package com.coursesystem.util;

import org.springframework.http.HttpHeaders;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public final class HttpUtil {

    private static final String HEADER_ALERT= "X-course-system-alert";
    private static final String HEADER_PARAMS = "X-course-system-params";

    private HttpUtil() {
    }

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HEADER_ALERT, message);

        try {
            headers.add(HEADER_PARAMS, URLEncoder.encode(param, StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException var5) {}

        return headers;
    }

    public static HttpHeaders createEntityCreationAlert(String entityName, String param) {
        String message = entityName + " was created with id " + param;
        return createAlert(message, param);
    }

    public static HttpHeaders createEntityDeletionAlert(String entityName, String param) {
        String message = entityName + " was deleted";
        return createAlert(message, param);
    }

    public static HttpHeaders createEntityUpdateAlert(String entityName, String param) {
        String message = entityName + " was updated";
        return createAlert(message, param);
    }
}