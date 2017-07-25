package com.ftlh.wechat.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64Util {
	
	static String toBase64String(String orig){
		return Base64.getEncoder().encodeToString(orig.getBytes(StandardCharsets.UTF_8));
	}
	static String base64Decode(String Base64String){
		return new String(Base64.getDecoder().decode(Base64String),StandardCharsets.UTF_8);
	}
	static byte[] base64ToArray(String base64String){
		return Base64.getDecoder().decode(base64String);
	} 
	
}
