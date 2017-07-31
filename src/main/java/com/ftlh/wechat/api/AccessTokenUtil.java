package com.ftlh.wechat.api;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.ftlh.wechat.http.HttpClientService;
import com.ftlh.wechat.http.PropertyUtil;

@Component
public class AccessTokenUtil {

	@Autowired
	private HttpClientService service;
	private static final Logger logger = LoggerFactory.getLogger(AccessToken.class);

	public AccessToken getAccessToken() {
		String appid = PropertyUtil.getProperty("appid");
		String secret = PropertyUtil.getProperty("secret");
		String url = PropertyUtil.getProperty("tokenurl");
		String grant_type = PropertyUtil.getProperty("grant_type");
		HashMap<String, String> params = new HashMap<>();
		if (appid != null) {
			params.put("appid", appid);
		} else {
			logger.error("appid ==null");
			return null;
		}
		if (secret != null) {
			params.put("secret", secret);
		} else {
			logger.error("secret ==null");
			return null;
		}
		if (grant_type != null) {
			params.put("grant_type", grant_type);
		} else {
			logger.error("grant_type ==null");
			return null;
		}

		AccessToken token = null;
		String res = null;
		try {
			res = service.doGet(url, params);
			// logger.info(res);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		token = JSON.parseObject(res, AccessToken.class);
		logger.info("token====={}THread====", token.access_token);
		return token;
	}

	public JsApiTicket geTicket(String accesstoken) {

		String ticketurl = PropertyUtil.getProperty("ticketurl");
		String type = PropertyUtil.getProperty("type");

		HashMap<String, String> params = new HashMap<>();
		if (accesstoken != null) {
			params.put("access_token", accesstoken);
		} else {
			logger.error("access_token ==null");
			return null;
		}
		if (type != null) {
			params.put("type", type);
		} else {
			logger.error("type ==null");
			return null;
		}
		String res = null;
		JsApiTicket ticket = null;
		try {
			res = service.doGet(ticketurl, params);
			logger.info(res);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ticket = JSON.parseObject(res, JsApiTicket.class);
		return ticket;
	}

}
