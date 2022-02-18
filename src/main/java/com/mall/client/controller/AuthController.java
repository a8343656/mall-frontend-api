package com.mall.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.auth.LoginDTO;
import com.mall.client.dto.auth.LogoutDTO;
import com.mall.client.dto.auth.RegisterDTO;
import com.mall.client.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	
	@Autowired AuthService authService;
	
	@PostMapping("/register")
	public ActionResult register (@RequestBody @Validated RegisterDTO data ) {
		
		return authService.register(data);
		
	}
	
	@PostMapping("/login")
	public ActionResult login (@RequestBody @Validated LoginDTO data) {
		
		return authService.login(data);
		
	}
	
	@PostMapping("/logout")
	public ActionResult logout (@RequestBody @Validated LogoutDTO data) {
		
		return authService.logout(data);
		
	}
}
