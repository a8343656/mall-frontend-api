package com.mall.client.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mall.client.dto.ActionResult;
import com.mall.client.dto.user.ChangeMemberDataDTO;
import com.mall.client.dto.user.ChangePwsDTO;
import com.mall.client.dto.user.GetOrderListDTO;
import com.mall.client.dto.user.GetShoppingCarDTO;
import com.mall.client.service.UserService;
import com.mall.client.service.UtilService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired UserService userService;
	@Autowired UtilService utilService;

	@PutMapping("/changeMemberData")
	public ActionResult changeMemberData (@RequestBody @Validated ChangeMemberDataDTO changeData ) {
		
		return userService.changeMemberData(changeData);
		
	}
	
	@PutMapping("/changePws")
	public ActionResult changeMemberData (@RequestBody @Validated ChangePwsDTO changeData ) {
		
		return userService.changePws(changeData);
		
	}
	
	@PostMapping("/getShoppingCarList")
	public ActionResult getShoppingCarList (@RequestBody @Validated GetShoppingCarDTO data ) {
		
		Pageable pageable = utilService.pageRequest(data.getPage(),8,data.getSortCol(),"DESC");
		return userService.getShoppingCarList(data.getUserId(),pageable);
		
	}
	
	@PostMapping("/getOrderList")
	public ActionResult getOrderList (@RequestBody @Validated GetOrderListDTO data ) {
		
		Pageable pageable = utilService.pageRequest(data.getPage(),8,data.getSortCol(),"DESC");
		return userService.getOrderList(data.getUserId(),pageable);
		
	}
	
}
