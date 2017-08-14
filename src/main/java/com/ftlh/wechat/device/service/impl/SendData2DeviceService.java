package com.ftlh.wechat.device.service.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.ftlh.wechat.api.AccessTokenService;
import com.ftlh.wechat.controller.dto.WechatDto;
import com.ftlh.wechat.http.HttpClientService;
import com.ftlh.wechat.http.HttpResult;
import com.ftlh.wechat.http.PropertyUtil;
import com.ftlh.wechat.util.DeviceBase64Util;
import com.ftlh.wechat.util.TimeUtil;

@Service
public class SendData2DeviceService {

	private static final Logger logger = LoggerFactory.getLogger(SendData2DeviceService.class);

	@Autowired
	HttpClientService service;

	@Autowired
	private AccessTokenService tokenService;

	private WechatDto _sendData2Device(String open_id, String base64content, String device_id, String device_type) {
		String access_token = AccessTokenService.getAccesstoken().getAccess_token();
		HashMap<String, String> params = new HashMap<>();
		params.put("device_type", device_type);
		params.put("device_id", device_id);
		params.put("open_id", open_id);
		params.put("content", base64content);
		String url = PropertyUtil.getProperty("postdataurl") + "access_token=" + access_token;
		HttpResult returnstring = null;
		try {
			returnstring = service.doPostJson(url, JSON.toJSONString(params));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.info(returnstring.getData());
		WechatDto jsonObject = (WechatDto) JSON.parseObject(returnstring.getData(), WechatDto.class);
		logger.info(jsonObject.toString());
		return jsonObject;

	}

	public WechatDto setDeviceTime(String open_id, String device_id, String device_type) {
		String base64value = DeviceBase64Util.bytes2Base64(setTime2DeviceData());
		HashMap<String, String> params = new HashMap<>();
		params.put("device_type", device_type);
		params.put("device_id", device_id);
		params.put("open_id", open_id);
		params.put("content", base64value);
		System.err.println(JSON.toJSONString(params));
		return _sendData2Device(open_id, base64value, device_id, device_type);
	}

	public WechatDto getDeviceTime(String open_id, String device_id, String device_type) {
		String base64value = DeviceBase64Util.bytes2Base64(getTime2DeviceData());
		HashMap<String, String> params = new HashMap<>();
		params.put("device_type", device_type);
		params.put("device_id", device_id);
		params.put("open_id", open_id);
		params.put("content", base64value);
		System.err.println(JSON.toJSONString(params));
		return _sendData2Device(open_id, base64value, device_id, device_type);
	}

	public WechatDto setConuntTarget2Device(String open_id, String device_id, String device_type, int target,
			short time) {
		String base64value = DeviceBase64Util.bytes2Base64(setCountTarge2DeviceData(target, time));
		HashMap<String, String> params = new HashMap<>();
		params.put("device_type", device_type);
		params.put("device_id", device_id);
		params.put("open_id", open_id);
		params.put("content", base64value);
		System.err.println(JSON.toJSONString(params));
		return _sendData2Device(open_id, base64value, device_id, device_type);
	}

	public static byte[] setCountTarge2DeviceData(int target, short time) {
		byte[] data = new byte[2];
		data[0] = 0x01;
		data[1] = 0x06;
		byte[] value = intToByteArrayReverse(target);
		byte[] timevalue = shortToByteArrayReverse(time);
		byte[] data3 = new byte[data.length + value.length + timevalue.length];
		System.arraycopy(data, 0, data3, 0, data.length);
		System.arraycopy(value, 0, data3, data.length, value.length);
		System.arraycopy(timevalue, 0, data3, data.length + value.length, timevalue.length);
		logger.info(bytes2hex(data3));
		return data3;
	}

	public static void main(String[] args) {
		SendData2DeviceService.setCountTarge2DeviceData(513, (short) 30);
	}

	private byte[] getTime2DeviceData() {
		byte[] data = new byte[2];
		data[0] = 0x03;
		data[1] = 0x00;
		return data;
	}

	/**
	 * 方式二
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2hex(byte[] bytes) {
		StringBuilder sb = new StringBuilder();
		String tmp = null;
		for (byte b : bytes) {
			// 将每个字节与0xFF进行与运算，然后转化为10进制，然后借助于Integer再转化为16进制
			tmp = Integer.toHexString(0xFF & b);
			if (tmp.length() == 1)// 每个字节8为，转为16进制标志，2个16进制位
			{
				tmp = "0" + tmp;
			}
			sb.append(tmp);
		}
		return sb.toString();
	}

	public static byte[] setTime2DeviceData() {
		byte[] data = new byte[4];
		byte[] head = new byte[2];
		head[0] = 0x03;
		head[1] = 0x04;
		int now = (int) TimeUtil.getSends2000Tonow();
		data = intToByteArray(now);
		byte[] data3 = new byte[head.length + data.length];
		System.arraycopy(head, 0, data3, 0, head.length);
		System.arraycopy(data, 0, data3, head.length, data.length);
		System.err.println(bytes2hex(data3));
		return data3;
	}

	// byte 数组与 int 的相互转换
	private static int byteArrayToInt(byte[] b) {
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}

	// byte 数组与 int 的相互转换
	private static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}

	// short to byte array
	private static byte[] shortToByteArray(short a) {
		return new byte[] { (byte) ((a >> 8) & 0xFF), (byte) (a & 0xFF) };
	}

	// int to byte and reverse the byte low to high
	private static byte[] intToByteArrayReverse(int a) {
		return new byte[] { (byte) (a & 0xFF), (byte) ((a >> 8) & 0xFF), (byte) ((a >> 16) & 0xFF),
				(byte) ((a >> 24) & 0xFF) };
	}

	// short to byte and reverse the low to high
	private static byte[] shortToByteArrayReverse(short a) {
		return new byte[] { (byte) (a & 0xFF), (byte) ((a >> 8) & 0xFF) };
	}
}
