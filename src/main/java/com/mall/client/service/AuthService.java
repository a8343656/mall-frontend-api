package com.mall.client.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.client.dto.ActionResult;
import com.mall.client.entity.MallUser;
import com.mall.client.repository.UserRepository;
import com.mall.client.ErrorCode;

@Service
public class AuthService {
	
	@Autowired UserRepository userRepository;
	
	public ActionResult register (MallUser user) {
		
		//搜尋帳號是否重複
		List<MallUser> dataList = userRepository.findByAccount(user.getAccount());
		
		if(!dataList.isEmpty()) {
			return new ActionResult(false,ErrorCode.ACCOUNT_DUPLICATE.getCode(),ErrorCode.ACCOUNT_DUPLICATE.getMsg());
		}
		
		//密碼加密後創建帳號
		String md5Pws = getMD5(user.getPassWord());
		user.setPassWord(md5Pws);
		userRepository.save(user);
		
		//回傳成功訊息
		return new ActionResult(true);
	}
	
	private String getMD5(String str) {
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
