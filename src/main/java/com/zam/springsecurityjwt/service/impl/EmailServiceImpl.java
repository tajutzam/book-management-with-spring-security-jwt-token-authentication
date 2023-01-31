package com.zam.springsecurityjwt.service.impl;

import com.zam.springsecurityjwt.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${application.baseurl}")
    private String baseUrl;

    @Override
    public boolean sendEmail(String to, String subject , String url) {
      try {
          MimeMessage mimeMessage = javaMailSender.createMimeMessage();
          MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8" );

          helper.setText("<html> <body><h3> To confirm your account, please click here </h3> <br><br> http://localhost:8080/api/v1/auth/"+url+" </body></html>",true);
          helper.setTo(to);
          helper.setSubject("activation account zam book management");
          helper.setFrom("abc@gmail.com");

          javaMailSender.send(mimeMessage);
          System.out.println("send success");
          return true;
      }catch (Exception e){
          System.out.println(e.getMessage());
          return false;
      }
    }
}
