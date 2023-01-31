package com.zam.springsecurityjwt.controller;


import com.zam.springsecurityjwt.service.impl.SchoolService;
import com.zam.springsecurityjwt.util.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/school/")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;


    @GetMapping()
    public ResponseEntity<Object> findAll(){
        return schoolService.findAll()
                .map(schools -> Helper.generateResponse("school found it", HttpStatus.OK, schools))
                .orElse(Helper.generateResponse("school empty", HttpStatus.NOT_FOUND, null));
    }
}
