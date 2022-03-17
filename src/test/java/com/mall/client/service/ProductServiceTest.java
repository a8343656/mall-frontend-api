package com.mall.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.entity.Product;
import com.mall.client.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {
	
	@Autowired ProductService productService;
	@MockBean ProductRepository productRepository;
	
	//查詢產品清單
	@Test
	void testGetProductListSuccess() {
		
		Product product1 = new Product();
		product1.setName("test");
		List<Product> productList = new ArrayList<>();
		productList.add(product1);
		Page<Product> pagedResponse = new PageImpl<>(productList);
		
		Mockito.when(productRepository.findByIsBuyable(Mockito.any(),Mockito.any())).thenReturn(pagedResponse);
		
		PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("name").descending());
		
		ActionResult result = productService.getProductList(pageRequest);
		Assertions.assertNotNull(result.getData(), "data should not null");
		@SuppressWarnings("unchecked")
		Page<Product> data = (Page<Product>)result.getData();
		Assertions.assertEquals(data.getContent().get(0).getName(),"test","name not correct");

	}
	
	//取得商品細節，商品無法購買
	@Test
	void testGetProductDetailNotAvaiable() {
		
		Mockito.when(productRepository.findByIdAndIsBuyableAndAmountGreaterThanEqual(Mockito.any(),Mockito.any(),Mockito.any()))
				.thenReturn(null);
		
		ActionResult result = productService.getProductDetail(1L);
		Assertions.assertEquals(result.getErrorCode(),ErrorCode.PRODUCT_NOT_FOUND.getCode(),"errocode not correct");
		
	}
	
	//正常取得商品細節
	@Test
	void testGetProductDetailSuccess() {
		
		Product dbProduct = new Product();
		dbProduct.setId(1L);
		dbProduct.setName("test");
		
		Mockito.when(productRepository.findByIdAndIsBuyableAndAmountGreaterThanEqual(Mockito.any(),Mockito.any(),Mockito.any()))
				.thenReturn(dbProduct);
		
		ActionResult result = productService.getProductDetail(1L);
		Assertions.assertNotNull(result.getData(), "data should not null");
		Product product = (Product)result.getData();
		Assertions.assertEquals(product.getName(),"test","product name not correct");
		
	}

}
