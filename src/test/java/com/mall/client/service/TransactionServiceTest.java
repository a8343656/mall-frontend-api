package com.mall.client.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.transaction.BuylistDTO;
import com.mall.client.dto.transaction.BuylistDetailDTO;
import com.mall.client.dto.transaction.CancelOrderDto;
import com.mall.client.entity.Buylist;
import com.mall.client.entity.Product;
import com.mall.client.exception.CantBuyException;
import com.mall.client.repository.BuyListRepository;
import com.mall.client.repository.ProductRepository;

@SpringBootTest
public class TransactionServiceTest {
	@Autowired TransactionService transactionService;
	@MockBean CheckService checkService;
	@MockBean ProductRepository productRepository;
	@MockBean BuyListRepository buyListRepository;
	
	// 購買遇到樂觀鎖
	@Test
	void testBuyOptimisticLock() {
		Product dbProduct = new Product();
		dbProduct.setAmount(10);
		Mockito.when(checkService.isUserPresentAndBuyAble(Mockito.any())).thenReturn(new ActionResult(true));
		Mockito.when(productRepository.findByIdAndIsBuyableAndAmountGreaterThanEqual(Mockito.any(),Mockito.any(),Mockito.any()))
		.thenReturn(dbProduct);
		Mockito.when(productRepository.save(Mockito.any())).thenThrow(new ObjectOptimisticLockingFailureException("", null));
		
		BuylistDTO buylist = new BuylistDTO();
		List<BuylistDetailDTO> detailList = new ArrayList<>();
		BuylistDetailDTO detail = new BuylistDetailDTO();
		detail.setAmount(1);
		detailList.add(detail);
		buylist.setDetailList(detailList);
		
		CantBuyException ex = Assertions.assertThrows(CantBuyException.class, 
				()->transactionService.buy(buylist));
		Assertions.assertEquals("data time out" , ex.getMessage());
		
	}
	
	// 商品無法購買
	@Test
	void testBuyProductNotAvailable() {
		Mockito.when(checkService.isUserPresentAndBuyAble(Mockito.any())).thenReturn(new ActionResult(true));
		Mockito.when(productRepository.findByIdAndIsBuyableAndAmountGreaterThanEqual(Mockito.any(),Mockito.any(),Mockito.any()))
		.thenReturn(null);
		
		BuylistDTO buylist = new BuylistDTO();
		List<BuylistDetailDTO> detailList = new ArrayList<>();
		BuylistDetailDTO detail = new BuylistDetailDTO();
		detail.setAmount(1);
		detailList.add(detail);
		buylist.setDetailList(detailList);
		
		CantBuyException ex = Assertions.assertThrows(CantBuyException.class, 
				()->transactionService.buy(buylist));
		Assertions.assertEquals("product can't buy" , ex.getMessage());
	}
	
	// 正常購買
	@Test
	void testBuySuccess() {
		
		Product dbProduct = new Product();
		dbProduct.setAmount(10);
		
		Mockito.when(checkService.isUserPresentAndBuyAble(Mockito.any())).thenReturn(new ActionResult(true));
		Mockito.when(productRepository.findByIdAndIsBuyableAndAmountGreaterThanEqual(Mockito.any(),Mockito.any(),Mockito.any()))
		.thenReturn(dbProduct);
		
		BuylistDTO buylist = new BuylistDTO();
		List<BuylistDetailDTO> detailList = new ArrayList<>();
		BuylistDetailDTO detail = new BuylistDetailDTO();
		detail.setAmount(1);
		detailList.add(detail);
		buylist.setDetailList(detailList);
		
		ActionResult result = transactionService.buy(buylist);		
		Assertions.assertTrue(result.isSuccess());
		
	}
	
	// 該訂單不存在
	@Test
	void testCancelOrderNotFound() {
		
		Mockito.when(buyListRepository.findByIdAndUserId(Mockito.any(),Mockito.any())).thenReturn(null);
		
		CancelOrderDto cancelData = new CancelOrderDto();
		cancelData.setOrderId(1L);
		cancelData.setUserId(1L);
		
		ActionResult result = transactionService.cancelOrder(cancelData);
		Assertions.assertEquals(result.getErrorCode(),ErrorCode.DATA_NOT_FOUND.getCode(),"errocode not correct");
		
	}
	
	// 寄送中無法取消
	@Test
	void testCancelOrderProductSending() {
		
		Buylist dbBuylist = new Buylist();
		dbBuylist.setStatus(1);
		Mockito.when(buyListRepository.findByIdAndUserId(Mockito.any(),Mockito.any())).thenReturn(dbBuylist);
		
		CancelOrderDto cancelData = new CancelOrderDto();
		cancelData.setOrderId(1L);
		cancelData.setUserId(1L);
		
		ActionResult result = transactionService.cancelOrder(cancelData);
		Assertions.assertEquals(result.getErrorCode(),ErrorCode.ORDER_CANT_CANCEL.getCode(),"errocode not correct");
		
	}
	
	//正常取消
	@Test
	void testCancelOrderSuccess() {
		
		Buylist dbBuylist = new Buylist();
		dbBuylist.setStatus(0);
		Mockito.when(buyListRepository.findByIdAndUserId(Mockito.any(),Mockito.any())).thenReturn(dbBuylist);
		
		CancelOrderDto cancelData = new CancelOrderDto();
		cancelData.setOrderId(1L);
		cancelData.setUserId(1L);
		
		ActionResult result = transactionService.cancelOrder(cancelData);
		Assertions.assertTrue(result.isSuccess());
		
	}
	
}
