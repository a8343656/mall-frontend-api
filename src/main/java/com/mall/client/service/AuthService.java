package com.mall.client.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.time.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.auth.LoginDTO;
import com.mall.client.dto.auth.LogoutDTO;
import com.mall.client.dto.auth.RegisterDTO;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.UserLoginData;
import com.mall.client.repository.UserLoginDataRepository;
import com.mall.client.repository.UserRepository;
import com.mall.client.ErrorCode;

@Service
public class AuthService {
	
	@Autowired UserRepository userRepository;
	@Autowired UserLoginDataRepository userLoginDataRepository;
	@Autowired UtilService utilService;
	@Autowired ModelMapper modelMapper;
	
	public ActionResult register (RegisterDTO data) {
		
		MallUser user = modelMapper.map(data,MallUser.class);
		//搜尋帳號是否重複
		List<MallUser> dataList = userRepository.findByAccount(user.getAccount());
		
		if(!dataList.isEmpty()) {
			return new ActionResult(false,ErrorCode.ACCOUNT_DUPLICATE.getCode(),ErrorCode.ACCOUNT_DUPLICATE.getMsg());
		}
		
		//密碼轉為MD5加密，並將帳號預設為啟用與可以購物
		String md5Pws = utilService.getMD5(user.getPassword());
		
		user.setPassword(md5Pws);
		user.setIsEnable("1");
		user.setIsShopable("1");
		
		userRepository.save(user);
		
		//回傳成功訊息
		return new ActionResult(true);

	}
	
	public ActionResult login (LoginDTO data) {
		
		MallUser user = modelMapper.map(data,MallUser.class);
		
		String md5Pws = utilService.getMD5(user.getPassword());
		user.setPassword(md5Pws);
		
		//查詢該帳號是否存在
		List<MallUser> dataList = userRepository.findByAccountAndPassword(user.getAccount(),user.getPassword());
		
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
		dataMap.put("userId", dbUser.getId().toString());
		dataMap.put("token", token);
		return new ActionResult(true,dataMap);
	}
	
	public ActionResult logout (LogoutDTO data) {
		//移除該筆登入資料
		userLoginDataRepository.deleteByUserId(data.getUserId());
		
		//回傳成功
		return new ActionResult(true);
	}
	

}
