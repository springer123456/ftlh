package com.ftlh.wechat.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.aspectj.weaver.ast.Var;

public class DeviceBase64Util {
	// string to base64string
	public static String toBase64String(String orig) {
		return Base64.getEncoder().encodeToString(orig.getBytes(StandardCharsets.UTF_8));
	}

	// base64 string to stirng ;
	public	static String base64Decode(String Base64String) {
		return new String(Base64.getDecoder().decode(Base64String), StandardCharsets.UTF_8);
	}

	// base64 to bytes[]
	public static byte[] base64ToArray(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}

	// bytes[] to base64
	public	static String bytes2Base64(byte[] bytes) {
		
		return Base64.getEncoder().encodeToString(bytes);
	}

	public static void main(String[] args) {

		String base64string = "AwRNC+kg";

		byte[] arry = DeviceBase64Util.base64ToArray(base64string);
		String hex = bytes2hex(arry);
		System.err.println(hex);

		long sends = TimeUtil.getSends2000Tonow();
		System.err.println(sends);
		setTime2DeviceData();

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

	public static  byte[] setTime2DeviceData() {
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
	public static int byteArrayToInt(byte[] b) {
		return b[3] & 0xFF | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
	}

	public static byte[] intToByteArray(int a) {
		return new byte[] { (byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF),
				(byte) (a & 0xFF) };
	}
}
