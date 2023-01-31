package com.zam.springsecurityjwt.service.impl;

import com.zam.springsecurityjwt.entity.School;

import java.util.List;
import java.util.Optional;

public interface SchoolService {

    public Optional<List<School>> findAll();

}
