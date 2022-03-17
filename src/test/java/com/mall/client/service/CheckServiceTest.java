package com.mall.client.service;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.Product;
import com.mall.client.exception.CantBuyException;
import com.mall.client.repository.ProductRepository;
import com.mall.client.repository.UserRepository;

@SpringBootTest
public class CheckServiceTest {
	@Autowired CheckService checkService;
	@MockBean UserRepository userRepository;
	@MockBean ProductRepository productRepository;
	
	//使用者不存在
	@Test
	void testIsUserPresentAndBuyAbleUserNotFound(){
		
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		ActionResult result = checkService.isUserPresentAndBuyAble(1L);
		Assertions.assertEquals(result.getErrorCode(),ErrorCode.USER_ID_NOT_FOUND.getCode(),"errocode not correct");
		
	}
	
	//使用者被禁用
	@Test
	void testIsUserPresentAndBuyAbleUserBan(){
		
		MallUser dbUser = new MallUser();
		dbUser.setIsEnable("0");
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(dbUser));
		
		ActionResult result = checkService.isUserPresentAndBuyAble(1L);
		Assertions.assertEquals(result.getErrorCode(),ErrorCode.USER_CANT_BUY_PRODUCT.getCode(),"errocode not correct");
		
	}
	
	//使用者正常
	@Test
	void testIsUserPresentAndBuyAbleSuccess(){
		
		MallUser dbUser = new MallUser();
		dbUser.setIsEnable("1");
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbUser));
		
		ActionResult result = checkService.isUserPresentAndBuyAble(1L);
		Assertions.assertTrue(result.isSuccess());
		
	}
	
	//商品不存在
	@Test
	void testIsProductBuyableProductNotFound(){
		
		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		CantBuyException ex = Assertions.assertThrows(CantBuyException.class, 
							()->checkService.isProductBuyable(1L , 10));
		Assertions.assertEquals("notFound" , ex.getMessage());
		
	}

	//商品下架
	@Test
	void testIsProductBuyableProductNotAvaiable(){
		
		Product dbProduct = new Product();
		dbProduct.setIsBuyable("0");
		
		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbProduct));
		
		CantBuyException ex = Assertions.assertThrows(CantBuyException.class, 
							()->checkService.isProductBuyable(1L , 10));
		Assertions.assertEquals("notAvaiable" , ex.getMessage());
		
	}
	
	//商品庫存不足
	@Test
	void testIsProductBuyableProductNotEnough(){
		
		Product dbProduct = new Product();
		dbProduct.setIsBuyable("1");
		dbProduct.setAmount(9);
		
		Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbProduct));
		
		CantBuyException ex = Assertions.assertThrows(CantBuyException.class, 
							()->checkService.isProductBuyable(1L , 10));
		Assertions.assertEquals("notEnough" , ex.getMessage());
		
	}
}
