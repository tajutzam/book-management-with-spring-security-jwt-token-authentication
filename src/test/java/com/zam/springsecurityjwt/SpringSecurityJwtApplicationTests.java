package com.zam.springsecurityjwt;

import com.zam.springsecurityjwt.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringSecurityJwtApplicationTests {

	@Autowired
	private EmailService emailService;

	@Test
	void contextLoads() {
	}

	@Test
	void testSendEmail() {
		emailService.sendEmail("safiraput66@gmail.com"  , "hai" , "www.google.com");
	}
}
