package com.zam.springsecurityjwt.exeptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@AllArgsConstructor
@Data
public class ApiExceptions {
    private String message;
    private Throwable cause;
    private HttpStatus httpStatus;
    private ZonedDateTime zonedDateTime;
}
