package com.upex.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtils {

	public static String sha256(String str) {
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			encdeStr = byteTohex(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}
	
	/**
	 * 将二进制比特数组转化为字符串
	 * 
	 * @param b
	 * @return
	 */
	private static String byteTohex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (byte element : b) {
			stmp = (Integer.toHexString(element & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs;
	}
}
