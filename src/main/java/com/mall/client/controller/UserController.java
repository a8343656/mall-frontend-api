package com.mall.client.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mall.client.dto.ActionResult;
import com.mall.client.dto.user.ChangeMemberDataDTO;
import com.mall.client.dto.user.ChangePwsDTO;
import com.mall.client.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired ModelMapper modelMapper;
	@Autowired UserService userService;

	@PutMapping("/changeMemberData")
	public ActionResult changeMemberData (@RequestBody @Validated ChangeMemberDataDTO changeData ) {
		
		return userService.changeMemberData(changeData);
		
	}
	
	@PutMapping("/changePws")
	public ActionResult changeMemberData (@RequestBody @Validated ChangePwsDTO changeData ) {
		
		return userService.changePws(changeData);
		
	}
	
}
