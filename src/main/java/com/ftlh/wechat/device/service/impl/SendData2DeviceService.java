package com.ftlh.wechat.device.service.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ftlh.wechat.api.AccessTokenService;
import com.ftlh.wechat.http.HttpClientService;
import com.ftlh.wechat.http.HttpResult;
import com.ftlh.wechat.http.PropertyUtil;
import com.ftlh.wechat.util.DeviceBase64Util;
import com.ftlh.wechat.util.TimeUtil;

@Service
public class SendData2DeviceService {

	
	@Autowired
	HttpClientService service;
	
	@Autowired
	private AccessTokenService tokenService;
	
	 private String _SendData2DeviceService(String open_id,String base64content,String device_id,String device_type){
//		 https://api.weixin.qq.com/device/transmsg?access_token=ACCESS_TOKEN
		 
		 String access_token= AccessTokenService.getAccesstoken().getAccess_token();
		 HashMap<String, String> params = new HashMap<>();
		 params.put("device_type",device_type);
		 params.put("device_id",device_id);
		 params.put("open_id",open_id);
		 params.put("content",base64content);
		 System.err.println(JSON.toJSONString(params));
		 String url =PropertyUtil.getProperty("postdataurl")+"access_token="+access_token;
		 System.err.println("url =="+url);
		 HttpResult returnstring = null;
		try {
			returnstring = service.doPostJson(url, JSON.toJSONString(params));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println(returnstring.toString());
		return returnstring.toString();
	 }
	 
	 public String setDeviceTime(String open_id,String device_id,String device_type){

		 String base64value= DeviceBase64Util.bytes2Base64(DeviceBase64Util.setTime2DeviceData());		 
		 HashMap<String, String> params = new HashMap<>();
		 params.put("device_type",device_type);
		 params.put("device_id",device_id);
		 params.put("open_id",open_id);
		 params.put("content",base64value);
		 System.err.println(JSON.toJSONString(params));
		 return 
		 _SendData2DeviceService(open_id, base64value, device_id, device_type);
	 }
	 
	
}
