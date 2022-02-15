package com.mall.client.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.client.dto.ActionResult;
import com.mall.client.dto.transaction.BuyDTO;
import com.mall.client.dto.transaction.BuyProduct;
import com.mall.client.entity.Buylist;
import com.mall.client.entity.BuylistDetail;
import com.mall.client.exception.CantBuyException;
import com.mall.client.entity.Product;
import com.mall.client.repository.BuyListRepository;
import com.mall.client.repository.ProductRepository;

@Service
public class TransactionService {
	
	@Autowired CheckService checkService;
	@Autowired ProductRepository productRepository;
	@Autowired BuyListRepository buyListRepository;
	
	@Transactional
	public ActionResult buy (BuyDTO buyData) {
		
		// 檢查該使用者是否存在，並確認是否有購買資格
		ActionResult checkResult = checkService.isUserPresentAndBuyAble(buyData.getUserId());
		if (!checkResult.isSuccess()) {
			return checkResult;
		}
		
		Buylist newBuylist = new Buylist();
		Set<BuylistDetail> detailSet = new HashSet<>();
		boolean pass = true;
		for (BuyProduct buyProduct : buyData.getBuyList()) {
			
			// 只在這邊做 read write lock 其他地方的讀取不設定，如果有 lock 代表有人正在買商品
			
			//確認該商品是否可購買，且庫存大於購買數量
			Product dbProduct = productRepository.findByIdAndIsBuyableAndAmountGreaterThan(buyProduct.getProductId(),"1" , 1);// where is_byable = 1
			
			if(dbProduct == null) {
				pass = false;
				break;
			}
			
			Integer newAmount = dbProduct.getAmount() - buyProduct.getAmount();
			
			if(newAmount < 0) {
				pass = false;
				break;
			}
			dbProduct.setAmount(newAmount);
			productRepository.save(dbProduct);
			
			
			//建立購買細項
			BuylistDetail buyListDetail = new BuylistDetail();
			buyListDetail.setOneProductPrice(buyProduct.getOneProductPrice());
			buyListDetail.setAmount(buyProduct.getAmount());
			buyListDetail.setProductId(buyProduct.getProductId());
			detailSet.add(buyListDetail);
			
			//release lock
		}
		newBuylist.setStatus(0);
		newBuylist.setUserId(buyData.getUserId());
		newBuylist.setTotalPrice(buyData.getTotalPrice());
		newBuylist.setUserBuylistDetail(detailSet);
		
		buyListRepository.save(newBuylist);
		
		
		// rollback
		if (pass == false) {
			throw new CantBuyException();
		}
		
		return new ActionResult(true);
	}
}
