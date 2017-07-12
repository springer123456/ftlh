package com.ftlh.wechat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.ftlh.wechat.api.AccessTokenService;
import com.ftlh.wechat.api.JsApiSign;
import com.ftlh.wechat.http.PropertyUtil;
@RestController
public class WechatBtController {

	private static final Logger logger = LoggerFactory.getLogger(WechatBtController.class);

	@Autowired
	private AccessTokenService tokenservice;
	
	@RequestMapping("/wxbt")
	
	public String wxbt(HttpServletRequest request){
		
		BufferedReader br = null;
		try {
			br = request.getReader();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			  String inputLine;
			       String str = "";
			     try {
			       while ((inputLine = br.readLine()) != null) {
			        str += inputLine;
			       }
			       br.close();
			     } catch (IOException e) {
			       System.out.println("IOException: " + e);
			     }
			     logger.info(str);
		
		return "";
		
	}

	
@RequestMapping("/wx")
	
	public String wx(HttpServletRequest request){
		
		BufferedReader br = null;
		try {
			br = request.getReader();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			  String inputLine;
			       String str = "";
			     try {
			       while ((inputLine = br.readLine()) != null) {
			        str += inputLine;
			       }
			       br.close();
			     } catch (IOException e) {
			       System.out.println("IOException: " + e);
			     }
			     logger.info(str);
		
		return "";
		
	}

@RequestMapping("device")
ModelAndView device(HttpServletRequest request,ModelAndView mav){
	
	String appid = PropertyUtil.getProperty("appid");
//	String secret = PropertyUtil.getProperty("secret");
	String domain= PropertyUtil.getProperty("domain");
   //1,获取ticket
	String ticket =AccessTokenService.getTicket().getTicket();
    Map<String,String> map = JsApiSign.sign(ticket, domain+"/device");
    
    request.setAttribute("timestamp", map.get("timestamp"));
    request.setAttribute("nonceStr", map.get("nonceStr"));
    request.setAttribute("signature", map.get("signature"));
    request.setAttribute("appid", appid);
	mav.setViewName("device");
	return mav;
}
}
