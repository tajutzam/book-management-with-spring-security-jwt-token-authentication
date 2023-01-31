package com.zam.springsecurityjwt.service;

public interface EmailService {

    public boolean sendEmail(String to , String subject , String url );

}
