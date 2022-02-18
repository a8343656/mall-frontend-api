package com.mall.client.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mall.client.dto.ActionResult;
import com.mall.client.dto.transaction.BuylistDTO;
import com.mall.client.entity.Buylist;
import com.mall.client.entity.BuylistDetail;
import com.mall.client.exception.CantBuyException;
import com.mall.client.entity.Product;
import com.mall.client.repository.BuyListRepository;
import com.mall.client.repository.ProductRepository;
import com.mall.client.util.ObjectMapperUtils;

@Service
public class TransactionService {
	
	@Autowired CheckService checkService;
	@Autowired ObjectMapperUtils objectMapperUtils;
	@Autowired ProductRepository productRepository;
	@Autowired BuyListRepository buyListRepository;
	@Autowired ModelMapper modelMapper;
	
	@Transactional
	public ActionResult buy (BuylistDTO buylistDTO) {
		
		Buylist newBuylist = modelMapper.map(buylistDTO, Buylist.class);
		List<BuylistDetail> newDetailList = objectMapperUtils.mapAll(buylistDTO.getBuyList(), BuylistDetail.class);
		newBuylist.setBuylistDetail(newDetailList);
		
		// 檢查該使用者是否存在，並確認是否有購買資格
		ActionResult checkResult = checkService.isUserPresentAndBuyAble(newBuylist.getUserId());
		if (!checkResult.isSuccess()) {
			return checkResult;
		}

		
		boolean pass = true;
		for (BuylistDetail detail : newBuylist.getBuylistDetail()) {
			
			detail.setUserBuylist(newBuylist);
			
			// 只在這邊做 read write lock 其他地方的讀取不設定，如果有 lock 代表有人正在買商品
			
			//確認該商品是否可購買，且庫存大於購買數量
			Product dbProduct = productRepository.findByIdAndIsBuyableAndAmountGreaterThan(detail.getProductId(),"1" , 1);// where is_byable = 1
			
			if(dbProduct == null) {
				pass = false;
				break;
			}
			
			Integer newAmount = dbProduct.getAmount() - detail.getAmount();
			
			if(newAmount < 0) {
				pass = false;
				break;
			}
			dbProduct.setAmount(newAmount);
			productRepository.save(dbProduct);
			
		}
		newBuylist.setStatus(0);
		
		buyListRepository.save(newBuylist);
		
		
		// rollback
		if (pass == false) {
			throw new CantBuyException();
		}
		
		return new ActionResult(true);
	}
}
