package com.ftlh.wechat.util;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.mail.javamail.JavaMailSenderImpl;
@Component
public class EmailUtil {
	@Bean
	public JavaMailSenderImpl mailSender() {
	    JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
	    javaMailSenderImpl.setHost("smtp.gmail.com");
	    javaMailSenderImpl.setPort(25);//;("25");
	    javaMailSenderImpl.setUsername("my_mail@gmail.com");
	    javaMailSenderImpl.setPassword("my_password");
	    javaMailSenderImpl.setJavaMailProperties(javaMailProperties());
	    return javaMailSenderImpl;
	}

	public Properties javaMailProperties(){
	    Properties properties = new Properties();
	    // add more properties in the same way
	    properties.put("mail.transport.protocol", "smtp");
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.debug", "true");
	    return properties;
	}
}
