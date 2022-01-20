package com.mall.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mall.client.dto.ActionResult;
import com.mall.client.dto.transaction.BuyDTO;
import com.mall.client.entity.ProductOrder;
import com.mall.client.entity.Product;
import com.mall.client.repository.OrderRepository;
import com.mall.client.repository.ProductRepository;

@Service
public class TransactionService {
	
	@Autowired CheckService checkService;
	@Autowired ProductRepository productRepository;
	@Autowired OrderRepository orderRepository;
	public ActionResult buy (BuyDTO buyData) {
		
		// 檢查該使用者是否存在，並確認是否有購買資格
		ActionResult checkResult = checkService.isUserPresentAndBuyAble(buyData.getUserId());
		if (!checkResult.isSucess()) {
			return checkResult;
		}
			
		// 查詢該商品是否可被購買，數量是否足夠
		ActionResult checkResult2 = checkService.isProductBuyable(buyData.getProductId(),buyData.getAmount());
		if (!checkResult2.isSucess()) {
			return checkResult2;
		}
		
		//減少庫存數量
		Product buyProduct = productRepository.findById(buyData.getProductId()).get();
		buyProduct.setAmount(buyProduct.getAmount()-buyData.getAmount());
		productRepository.save(buyProduct);
		
		//建立訂單
		ProductOrder order = new ProductOrder();
		order.setUserId(buyData.getUserId());
		order.setProductId(buyData.getProductId());
		order.setStatus(0);
		order.setAmount(buyData.getAmount());
		orderRepository.save(order);
		
		return new ActionResult(true);
	}
}
