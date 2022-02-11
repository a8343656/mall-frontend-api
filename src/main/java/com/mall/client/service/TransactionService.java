package com.mall.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.client.dto.ActionResult;
import com.mall.client.dto.transaction.BuyDTO;
import com.mall.client.dto.transaction.Order;
import com.mall.client.entity.ProductOrder;
import com.mall.client.exception.CantBuyException;
import com.mall.client.entity.Product;
import com.mall.client.repository.ProductOrderRepository;
import com.mall.client.repository.ProductRepository;

@Service
public class TransactionService {
	
	@Autowired CheckService checkService;
	@Autowired ProductRepository productRepository;
	@Autowired ProductOrderRepository productOrderRepository;
	
	@Transactional
	public ActionResult buy (BuyDTO buyData) {
		
		// 檢查該使用者是否存在，並確認是否有購買資格
		ActionResult checkResult = checkService.isUserPresentAndBuyAble(buyData.getUserId());
		if (!checkResult.isSuccess()) {
			return checkResult;
		}
		
		for (Order order : buyData.getOrderList()) {
			
			// 查詢該商品是否可被購買，數量是否足夠
			try {
				checkService.isProductBuyable(order.getProductId(),order.getAmount());
			}catch(CantBuyException ex) {
				throw ex;
			}
			
			//減少庫存數量
			Product buyProduct = productRepository.findById(order.getProductId()).get();
			buyProduct.setAmount(buyProduct.getAmount()-order.getAmount());
			productRepository.save(buyProduct);
			
			//建立訂單
			ProductOrder dbOrder = new ProductOrder();
			dbOrder.setUserId(buyData.getUserId());
			dbOrder.setProductId(order.getProductId());
			dbOrder.setStatus(0);
			dbOrder.setAmount(order.getAmount());
			productOrderRepository.save(dbOrder);
		}

		
		return new ActionResult(true);
	}
}
