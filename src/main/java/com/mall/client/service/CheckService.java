package com.mall.client.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.Product;
import com.mall.client.exception.CantBuyException;
import com.mall.client.repository.ProductRepository;
import com.mall.client.repository.UserRepository;

@Service
public class CheckService {
	
	@Autowired UserRepository userRepository;
	@Autowired ProductRepository productRepository;

	public ActionResult isUserPresentAndBuyAble (Long userId) {
		
		// 檢查該使用者是否存在，並確認是否有購買資格
		Optional<MallUser> data = userRepository.findById(userId);
		
		if(!data.isPresent()) {
			return new ActionResult(false,ErrorCode.USER_ID_NOT_FOUND.getCode() ,ErrorCode.USER_ID_NOT_FOUND.getMsg());
		} 
		else if(data.get().getIsEnable() == "0" || data.get().getIsShopable() == "0") {
			return new ActionResult(false,ErrorCode.USER_CANT_BUY_PRODUCT.getCode() ,ErrorCode.USER_CANT_BUY_PRODUCT.getMsg());
		} else {
			return new ActionResult(true);
		}
		
	}
	
	public void isProductBuyable (Long productId, Integer buyAmount) {
		
		// 查詢該商品是否可被購買，數量是否足夠
		Optional<Product> data = productRepository.findById(productId);
		
		if(!data.isPresent()) {
			throw new CantBuyException("notFound");
		}
		Product product = data.get();
		
		if (product.getIsBuyable().equals("0")) {
			throw new CantBuyException("notAvaiable");

		} else if(product.getAmount() < buyAmount){
			throw new CantBuyException("notEnough");
		}
		
	}
}
