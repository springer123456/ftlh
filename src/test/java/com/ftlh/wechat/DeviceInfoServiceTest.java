package com.ftlh.wechat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ftlh.wechat.appconfig.DatabaseConfig;
import com.ftlh.wechat.appconfig.DatabaseConfig;
import com.ftlh.wechat.device.service.DeviceInfoServiceI;

@RunWith(SpringJUnit4ClassRunner.class)  

//@ContextConfiguration(value = "file:src/main/webapp/WEB-INF/spring/root-context.xml")  

@ContextConfiguration(classes = {DatabaseConfig.class})//,value = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class DeviceInfoServiceTest  {
	@Autowired
	DeviceInfoServiceI service;
	@Test
	public void getnewslist() throws Exception {
		try{
		service.selectByPrimaryKey(1L);
		}catch(Exception e){e.printStackTrace();}
	}
}
