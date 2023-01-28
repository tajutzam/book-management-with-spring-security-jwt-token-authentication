package com.zam.springsecurityjwt.exeptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ExceptionCustomHandler {


    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleCustomException(ApiRequestException e){
        ApiExceptions exceptions = new ApiExceptions(e.getMessage()
                , e.getCause()
                , HttpStatus.BAD_REQUEST
                , ZonedDateTime.now(ZoneId.of("Asia/Jakarta")));
        return ResponseEntity.badRequest().body(exceptions);
    }

}
