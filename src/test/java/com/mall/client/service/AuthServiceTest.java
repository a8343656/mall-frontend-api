package com.mall.client.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.auth.LoginDTO;
import com.mall.client.dto.auth.LogoutDTO;
import com.mall.client.dto.auth.RegisterDTO;
import com.mall.client.entity.MallUser;
import com.mall.client.repository.UserLoginDataRepository;
import com.mall.client.repository.UserRepository;


@SpringBootTest
public class AuthServiceTest {
	
	@Autowired
	AuthService authService;
	@Autowired 
	UtilService utilService;
	
	@MockBean UserRepository userRepository;
	@MockBean UserLoginDataRepository userLoginDataRepository;

	private ObjectMapper objectMapper = new ObjectMapper();
	
	//註冊帳號重複DUPLICATE
	@Test
	void testRegisterDuplicateAccount(){
		
		MallUser dbUser= new MallUser();
		dbUser.setAccount("test");
		List<MallUser> dbUserList = new ArrayList<>();
		dbUserList.add(dbUser);
		Mockito.when(userRepository.findByAccount("test")).thenReturn(dbUserList);
		
		RegisterDTO data = new RegisterDTO();
		data.setAccount("test");
		
		ActionResult result = authService.register(data);
		Assertions.assertEquals(result.getErrorCode(),ErrorCode.ACCOUNT_DUPLICATE.getCode(),"errocode not correct");
	}
	
	//註冊成功
	@Test
	void testRegisterSuccess(){
		
		String account = "test";
		String pws = "pws";
		Long id = 1L;
		MallUser newUser = new MallUser();
		newUser.setId(id);
		newUser.setAccount(account);
		newUser.setPassword(pws);
		List<MallUser> dbUserList = new ArrayList<>();
		dbUserList.add(newUser);

		Mockito.when(userRepository.findByAccount(account)).thenReturn(new ArrayList<>());
		Mockito.when(userRepository.findByAccountAndPassword(account,utilService.getMD5(pws))).thenReturn(dbUserList);
		
		RegisterDTO data = new RegisterDTO();
		data.setAccount(account);
		data.setPassword(pws);
		
		ActionResult result = authService.register(data);
		Assertions.assertTrue(result.isSuccess());
		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = objectMapper.convertValue(result.getData() ,Map.class );
		Assertions.assertEquals(dataMap.get("userId"), 1L);
		Assertions.assertNotNull(dataMap.get("token"));
		
	}
	
	//帳號鎖定
	@Test
	void testLoginAccountDisable(){
		String account = "test";
		String pws = "pws";

		MallUser dbUser= new MallUser();
		dbUser.setAccount(account);
		dbUser.setIsEnable("0");
		List<MallUser> dbUserList = new ArrayList<>();
		dbUserList.add(dbUser);
		Mockito.when(userRepository.findByAccountAndPassword(account,pws)).thenReturn(dbUserList);
			
		LoginDTO data = new LoginDTO();
		data.setAccount(account);
		data.setPassword(pws);

		ActionResult result = authService.login(data);
		Assertions.assertEquals(result.getErrorCode(),ErrorCode.ACCOUNT_BAN.getCode(),"errocode not correct");
			
	}
		
	//登入密碼錯誤
	@Test
	void testLoginPwsErr(){
		
		List<MallUser> emptyList = new ArrayList<>();
		Mockito.when(userRepository.findByAccountAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(emptyList);
		
		LoginDTO data = new LoginDTO();
		data.setAccount("test");
		data.setPassword("errPWs");

		ActionResult result = authService.login(data);
		Assertions.assertEquals(result.getErrorCode(),ErrorCode.ACCOUNT_OR_PWS_INCORRECT.getCode(),"errocode not correct");
	
	}
	
	//登入成功
	@Test
	void testLoginSuccess(){
		Long id = 1L;
		String account = "test";
		String pws = "pws";
		
		MallUser dbUser= new MallUser();
		dbUser.setId(id);
		dbUser.setAccount(account);
		dbUser.setPassword(utilService.getMD5(pws));
		List<MallUser> dbUserList = new ArrayList<>();
		dbUserList.add(dbUser);
		Mockito.when(userRepository.findByAccountAndPassword(account,utilService.getMD5(pws))).thenReturn(dbUserList);
		
		LoginDTO data = new LoginDTO();
		data.setAccount(account);
		data.setPassword(pws);
		
		ActionResult result = authService.login(data);
		Assertions.assertTrue(result.isSuccess());
		Assertions.assertNotNull(result.getData());
		
		@SuppressWarnings("unchecked")
		Map<String, Object> dataMap = objectMapper.convertValue(result.getData() ,Map.class );
		Assertions.assertEquals(dataMap.get("userId"), (long)1);
		Assertions.assertNotNull(dataMap.get("token"));
		
	}
	
	//登出成功
	void testLogoutSuccess(){
		Long userId = 1L;
		
		MallUser dbUser= new MallUser();
		dbUser.setId(userId);
		Mockito.when(userLoginDataRepository.deleteByUserId(userId));
		
		LogoutDTO data = new LogoutDTO();
		data.setUserId(userId);
		
		ActionResult result = authService.logout(data);
		Assertions.assertTrue(result.isSuccess());
		
	}
	
}
