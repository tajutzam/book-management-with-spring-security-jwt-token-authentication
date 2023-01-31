package com.zam.springsecurityjwt.exeptions;


import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

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
    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> fileToBig(ApiRequestException e){
        ApiExceptions exceptions = new ApiExceptions(e.getMessage()
                , e.getCause()
                , HttpStatus.BAD_REQUEST
                , ZonedDateTime.now(ZoneId.of("Asia/Jakarta")));
        return ResponseEntity.badRequest().body(exceptions);
    }
    @ExceptionHandler(value = FileUploadExceptions.class)
    public ResponseEntity<Object> fileTo(FileUploadExceptions e){
        ApiExceptions exceptions = new ApiExceptions(e.getMessage()
                , e.getCause()
                , HttpStatus.BAD_REQUEST
                , ZonedDateTime.now(ZoneId.of("Asia/Jakarta")));
        return ResponseEntity.badRequest().body(exceptions);
    }


}
