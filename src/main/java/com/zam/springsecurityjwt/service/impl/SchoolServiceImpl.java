package com.zam.springsecurityjwt.service.impl;

import com.zam.springsecurityjwt.entity.School;
import com.zam.springsecurityjwt.repo.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService{

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public Optional<List<School>> findAll() {
        List<School> schoolList = schoolRepository.findAll();
        if(schoolList.size() == 0){
            return Optional.empty();
        }
        return Optional.of(schoolList);
    }
}
