package com.mall.client.controller;

import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.transaction.BuylistDTO;
import com.mall.client.dto.transaction.CancelOrderDto;
import com.mall.client.dto.user.ChangePwsDTO;
import com.mall.client.dto.user.GetShoppingCarDTO;
import com.mall.client.exception.CantBuyException;
import com.mall.client.service.TransactionService;
import com.mall.client.service.UserService;
import com.mall.client.service.UtilService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired TransactionService transactionService;

	@PostMapping("/buy")
	public ActionResult buy (@RequestBody @Validated BuylistDTO buylistDTO ) {
		
		try {
			return transactionService.buy(buylistDTO);
		}catch (CantBuyException ex) {
			if(ex.getMessage().equals("data time out")) {
				return new ActionResult(false,ErrorCode.BUY_SYSTEM_BUSY.getCode() ,ErrorCode.BUY_SYSTEM_BUSY.getMsg());
			}
			return new ActionResult(false,ErrorCode.BUY_FAIL.getCode() ,ErrorCode.BUY_FAIL.getMsg());
		}
		
	}
	
	@PostMapping("/cancelOrder")
	public ActionResult cancelOrder (@RequestBody @Validated CancelOrderDto cancelOrderDto ) {
	
			return transactionService.cancelOrder(cancelOrderDto);

	}
	
}
