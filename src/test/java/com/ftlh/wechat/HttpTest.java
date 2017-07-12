package com.ftlh.wechat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ftlh.wechat.http.HttpClientService;

@RunWith(SpringJUnit4ClassRunner.class)  

@ContextConfiguration(value = "file:src/main/webapp/WEB-INF/spring/root-context.xml")  
//@ContextHierarchy({  
//        @ContextConfiguration(name = "parent", locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml"),  
//        @ContextConfiguration(name = "child", locations = "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")  
//})  
  
//注解风格  
//@RunWith(SpringJUnit4ClassRunner.class)  
//@WebAppConfiguration(value = "src/main/webapp")  
//@ContextHierarchy({  
//        @ContextConfiguration(name = "parent", classes = AppConfig.class),  
//        @ContextConfiguration(name = "child", classes = MvcConfig.class)  
//})  
public class HttpTest{
	
	
	@Autowired
	HttpClientService service;
	@Test
	public void getnewslist() throws Exception {
		long begin= System.currentTimeMillis();
		for(int i=0;i<100;i++){
		 	service.doGet("https://b.cnfic.com.cn/news/index.php/news/news_list?code=shmm&start=0&needCount=10&keyword=");
		}
		long end =System.currentTimeMillis();
		System.err.println("Time cost for 100 times of sending request ===="+(end-begin));
		
	}

}
