package com.mall.client.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mall.client.dto.ActionResult;
import com.mall.client.dto.auth.LoginDTO;
import com.mall.client.dto.auth.RegisterDTO;
import com.mall.client.entity.MallUser;
import com.mall.client.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired ModelMapper modelMapper;
	@Autowired AuthService authService;
	
	@PostMapping("/register")
	public ActionResult register (@RequestBody @Validated RegisterDTO data ) {
		
		MallUser user = modelMapper.map(data,MallUser.class);
		return authService.register(user);
		
	}
	
	@PostMapping("/login")
	public ActionResult login (@RequestBody @Validated LoginDTO data) {
		
		MallUser user = modelMapper.map(data,MallUser.class);
		return authService.login(user);
		
	}
}
