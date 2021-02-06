package com.yzy.demo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @Author firmye
 * @Date 2017年11月21日 上午11:16:09
 *
 * @Description 加密工具类
 */
public class EncodeUtil {

	public static String encodeBySHA256(String str) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256"); // 如果是SHA加密只需要将"SHA-1"改成"SHA"即可
		byte[] input = str.getBytes();
		byte[] buff = md.digest(input);
		return bytesToHex(buff);
	}
	
	/**
	 * 二进制转十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer str = new StringBuffer();
		// 把数组每一字节换成16进制连成MD5字符串
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			digital = bytes[i];

			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				str.append("0");
			}
			str.append(Integer.toHexString(digital));
		}
		return str.toString().toUpperCase();
	}

}
