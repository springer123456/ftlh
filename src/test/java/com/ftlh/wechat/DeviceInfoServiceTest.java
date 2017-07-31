package com.ftlh.wechat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ftlh.wechat.appconfig.CachingConfig;
import com.ftlh.wechat.appconfig.DatabaseConfig;
import com.ftlh.wechat.appconfig.DatabaseConfig;
import com.ftlh.wechat.device.service.DeviceInfoServiceI;

@RunWith(SpringJUnit4ClassRunner.class)

// @ContextConfiguration(value =
// "file:src/main/webapp/WEB-INF/spring/root-context.xml")

@ContextConfiguration(classes = { DatabaseConfig.class, CachingConfig.class }) // ,value
																				// =
																				// "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class DeviceInfoServiceTest {
	@Autowired
	DeviceInfoServiceI service;

	@Test
	public void getnewslist() throws Exception {
		try {
			long start1 = System.currentTimeMillis();
			service.selectByPrimaryKey(1L);
			long end1 = System.currentTimeMillis();
			System.err.println("time1===" + (end1 - start1));
			for (int i = 0; i < 10; i++) {
				long start3 = System.currentTimeMillis();
				service.selectByPrimaryKey(1L);
				long end3 = System.currentTimeMillis();
				System.err.println("time1===" + (end3 - start3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
