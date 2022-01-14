package com.mall.client.service;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service
public class UtilService {

	public String getMD5(String str) {
		
		try {
			// 生成一個MD5加密計算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");
			// 計算md5函式
			md.update(str.getBytes());

			// digest()最後確定返回md5 hash值，返回值為8為字串。因為md5 hash值是16位的hex值，實際上就是8位的字元
			// BigInteger函式則將8位的字串轉換成16位hex值，用字串來表示；得到字串形式的hash值
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {
			throw new RuntimeException("MD5加密出現錯誤");
		}

	}
}
