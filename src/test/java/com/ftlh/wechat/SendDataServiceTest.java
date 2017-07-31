package com.ftlh.wechat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Base64Utils;

import com.ftlh.wechat.device.service.impl.SendData2DeviceService;
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(value = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class SendDataServiceTest {

	@Autowired
	SendData2DeviceService service;

	@Test
	public void senddata() {

		String user = "oX7SXwaStmoARx0ZIaG5_GaXhF_U";
		String device_type = "gh_7334a0ab6e31";
		String device_id = "sportsage_test_bledevice_03";
		byte[] content = new byte[2];
		content[0] = Byte.valueOf("03", 16);
		content[1] = Byte.valueOf("00", 16);

		String base64content = Base64Utils.encodeToString(content);
		try {
			service.setDeviceTime(user, device_id, device_type);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
