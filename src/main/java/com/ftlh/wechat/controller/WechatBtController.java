package com.ftlh.wechat.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Case;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.ftlh.wechat.api.AccessTokenService;
import com.ftlh.wechat.api.JsApiSign;
import com.ftlh.wechat.http.PropertyUtil;
import com.ftlh.wechat.util.DeviceBase64Util;
import com.sun.xml.internal.ws.client.SenderException;
import com.ftlh.wechat.api.message.*;
import com.ftlh.wechat.api.message.SerializeXmlUtil;
import com.ftlh.wechat.api.message.WechatInPutMsg;
import com.thoughtworks.xstream.XStream;

@RestController
public class WechatBtController {

	private static final Logger logger = LoggerFactory.getLogger(WechatBtController.class);

	@Autowired
	private AccessTokenService tokenservice;

	@RequestMapping("/wxbt")

	public String wxbt(HttpServletRequest request) {

		// 处理接收消息
		WechatInPutMsg inputMsg = GetxmlMessage(request);
		System.err.println(JSON.toJSONString(inputMsg));
		// 基本信息
		String touser = inputMsg.getToUserName(); // 服务端
		String formuser = inputMsg.getFromUserName(); // 客户端
		long createTime = inputMsg.getCreateTime(); // 接收时间
		Long returnTime = Calendar.getInstance().getTimeInMillis() / 1000;// 返回时间
		StringBuilder str = null;// new StringBuilder(); // 返回的消息
		// 取得消息类型
		String msgType = inputMsg.getMsgType();
		// 根据消息类型获取对应的消息内容
		System.err.println("#####################msgtype#####################################\n" + inputMsg.getMsgType()
				+ "\n#####################msgtype#####################################\n");

		switch (msgType) {
		// Event消息处理
		case WxMessage_Type.EVENT:
			System.err.println("Event reched server" + inputMsg.getEvent());

			switch (inputMsg.getEvent()) {
			case WxMessage_Type.EVENT_CLICK:
				System.err.println("eventkey===" + inputMsg.getEventKey());
				// 确定是否已经绑定，如果没有绑定，提示先绑定用户信息
				break;
			default:
				break;
			}

		case WxMessage_Type.DEVICE_TEXT:
			
			String base64content = inputMsg.getContent();
			if(base64content!=null){
			byte[] content = Base64Utils.decodeFromString(base64content);
			
			}
			
		default:
			break;
		}
		return "";

	}

	@RequestMapping("/wx")

	public String wx(HttpServletRequest request) {

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

	/**
	 * @param request
	 * @return InputMessage 微信公众平台发送的xml转换为java对象之后的消息
	 */
	private WechatInPutMsg GetxmlMessage(HttpServletRequest request) {
		ServletInputStream in = null;
		try {
			in = request.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 将POST流转换为XStream对象
		XStream xs = SerializeXmlUtil.createXstream();
		// 将指定节点下的xml节点数据映射为对象
		xs.alias("xml", WechatInPutMsg.class);
		// 将流转换为字符串
		StringBuilder xmlMsg = new StringBuilder();
		byte[] b = new byte[4096];
		try {
			for (int n; (n = in.read(b)) != -1;) {
				xmlMsg.append(new String(b, 0, n, "UTF-8"));
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Enumeration<String> ea = request.getHeaderNames();
		logger.debug("********************headers**********************\n");
		while (ea.hasMoreElements()) {
			String name = (String) ea.nextElement();
			String value = request.getHeader(name);
			System.out.println(name + " = " + value + "\n");
		}
		logger.debug("********************headers end**********************\n");

		logger.info("================form wx============\n" + xmlMsg.toString());
		// 将xml内容转换为InputMessage对象
		WechatInPutMsg inputMsg = (WechatInPutMsg) xs.fromXML(xmlMsg.toString());
		logger.debug("==================================after formate==========================\n"
				+ JSON.toJSONString(inputMsg));
		return inputMsg;
	}

	@RequestMapping("device")
	ModelAndView device(HttpServletRequest request, ModelAndView mav) {

		String appid = PropertyUtil.getProperty("appid");
		// String secret = PropertyUtil.getProperty("secret");
		String domain = PropertyUtil.getProperty("domain");
		// 1,获取ticket
		String ticket = AccessTokenService.getTicket().getTicket();
		Map<String, String> map = JsApiSign.sign(ticket, domain + "/device");

		request.setAttribute("timestamp", map.get("timestamp"));
		request.setAttribute("nonceStr", map.get("nonceStr"));
		request.setAttribute("signature", map.get("signature"));
		request.setAttribute("appid", appid);
		mav.setViewName("device");
		return mav;
	}

	/**
	 * 
	 * 把毫秒转化成日期
	 * 
	 * @param dateFormat(日期格式，例如：MM/dd/yyyy
	 *            HH:mm:ss)
	 * 
	 * @param millSec(毫秒数)
	 * 
	 * @return
	 * 
	 */

	public static String transferLongToDate(String dateFormat, Long millSec) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);

	}

	// 微信消息类型

	static final class WxMessage_Type {

		public static final String TEXT = "text";
		public static final String EVENT = "event";
		public static final String IMAGE = "image";
		public static final String VOICE = "voice";
		public static final String VIDEO = "video";
		public static final String LOCATION = "location";
		public static final String LINK = "link";
		public static final String SHORTVIDEO = "shortvideo";
		public static final String EVENT_SUBSCRIBE = "subscribe";
		public static final String EVENT_SCAN = "SCAN";
		public static final String EVENT_LOCATION = "LOCATION";
		public static final String EVENT_CLICK = "CLICK";
		public static final String EVENT_VIEW = "VIEW";
		public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
		public static final String EVENT_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";
		public static final String DEVICE_TEXT ="device_text";
		

		// public static final String ;
	}
	
}
