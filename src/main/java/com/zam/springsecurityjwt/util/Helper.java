package com.zam.springsecurityjwt.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }

    public static ResponseEntity<Object> generateResponse(String message , HttpStatus httpStatusCode , Object object){
        Map<String , Object> data = new HashMap<>();
        data.put("message" , message);
        data.put("code" , httpStatusCode);
        data.put("payload" , object);
        return ResponseEntity.status(httpStatusCode).body(data);
    }

}
