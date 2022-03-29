package com.mall.client.service;

import org.springframework.stereotype.Service;
import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.entity.Product;
import com.mall.client.repository.ProductRepository;
import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;

@Service
public class ProductService {
	
	@Autowired ProductRepository productRepository;

	public ActionResult getProductList (Pageable pageable) {
		try {
			
			// 依照分頁的資料，尋找所有可購買的商品
			Page<Product> productList = productRepository.findByIsBuyable("1",pageable);
			return new ActionResult(true , productList);
			
		}catch(PropertyReferenceException e) {
			return new ActionResult(false, 
			ErrorCode.SORT_COLUMN_ERR.getCode(), 
			MessageFormat.format(ErrorCode.PARAMETER_ERR.getMsg(),e.getMessage()));
		}
	}
	
	public ActionResult getProductDetail (Long id) {
			
		//找尋該id的商品，並確認該商品是否上架中
		Product data = productRepository.findByIdAndIsBuyable(id,"1");
		if(data == null) {
			return new ActionResult(false, ErrorCode.PRODUCT_NOT_FOUND.getCode(), ErrorCode.PRODUCT_NOT_FOUND.getMsg());
		}

		return new ActionResult(true , data);
		
	}
	

}
