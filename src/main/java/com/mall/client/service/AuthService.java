package com.mall.client.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mall.client.dto.ActionResult;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.UserLoginData;
import com.mall.client.repository.UserLoginDataRepository;
import com.mall.client.repository.UserRepository;
import com.mall.client.ErrorCode;

@Service
public class AuthService {
	
	@Autowired UserRepository userRepository;
	@Autowired UserLoginDataRepository userLoginDataRepository;
	
	public ActionResult register (MallUser user) {
		
		//搜尋帳號是否重複
		List<MallUser> dataList = userRepository.findByAccount(user.getAccount());
		
		if(!dataList.isEmpty()) {
			return new ActionResult(false,ErrorCode.ACCOUNT_DUPLICATE.getCode(),ErrorCode.ACCOUNT_DUPLICATE.getMsg());
		}
		
		//密碼轉為MD5加密，並將帳號預設為啟用與可以購物
		String md5Pws = getMD5(user.getPassWord());
		
		user.setPassWord(md5Pws);
		user.setIsEnable("1");
		user.setIsShopable("1");
		
		userRepository.save(user);
		
		//回傳成功訊息
		return new ActionResult(true);

	}
	
	public ActionResult login (MallUser user) {
		
		String md5Pws = getMD5(user.getPassWord());
		user.setPassWord(md5Pws);
		
		//查詢該帳號是否存在
		List<MallUser> dataList = userRepository.findByAccountAndPassWord(user.getAccount(),user.getPassWord());
		
		if(dataList.isEmpty()) {
			return new ActionResult(false,ErrorCode.ACCOUNT_OR_PWS_INCORRECT.getCode(),ErrorCode.ACCOUNT_OR_PWS_INCORRECT.getMsg());
		}
		MallUser dbUser = dataList.get(0);
		
		//檢查帳號是否被停用
		if(dbUser.getIsEnable() == "0") {
			return new ActionResult(false,ErrorCode.ACCOUNT_BAN.getCode(),ErrorCode.ACCOUNT_BAN.getMsg());
		}
		
		// 產生token 與 過期時間
		String token = UUID.randomUUID().toString();
		Date now = new Date();
		Date timeOutDate = DateUtils.addHours(now, 2);
		
		//刪除舊的資料並儲存新的登入資料
		userLoginDataRepository.deleteByUserId(dbUser.getId());
		UserLoginData loginData = new UserLoginData();
		loginData.setUserId(dbUser.getId());
		loginData.setToken(token);
		loginData.setValidTime(timeOutDate);
		userLoginDataRepository.save(loginData);
		
		//回傳 token 與成功訊息
		Map<String, String> dataMap = new HashMap<>();
		dataMap.put("token", token);
		return new ActionResult(true,dataMap);
	}
	
	public ActionResult logout (MallUser user) {
		//移除該筆登入資料
		userLoginDataRepository.deleteByUserId(user.getId());
		
		//回傳成功
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
