package com.mall.client.service;

import org.springframework.stereotype.Service;
import com.mall.client.ErrorCode;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.product.AddShoppingCarDTO;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.Product;
import com.mall.client.entity.ShoppingCar;
import com.mall.client.repository.ProductRepository;
import com.mall.client.repository.ShoppingCarRepository;
import com.mall.client.repository.UserRepository;
import java.text.MessageFormat;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;

@Service
public class ProductService {
	
	@Autowired ProductRepository productRepository;
	@Autowired UserRepository userRepository;
	@Autowired ShoppingCarRepository shoppingCarRepository;
	@Autowired CheckService checkService;
	@Autowired ModelMapper modelMapper;

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
	
	public ActionResult addToShoppingCar (AddShoppingCarDTO data) {
		
		// 檢查該使用者是否存在，並確認是否有購買資格
		ActionResult checkResult = checkService.isUserPresentAndBuyAble(data.getUserId());
		if (!checkResult.isSuccess()) {
			return checkResult;
		}
			
		// 查詢該商品是否可被購買，且庫存大於1
		ActionResult checkResult2 = checkService.isProductBuyable(data.getProductId(),1);
		if (!checkResult2.isSuccess()) {
			return checkResult2;
		}
		
		//查詢該物品是否已在購物車中，不存在才做儲存
		List<ShoppingCar> dbList = shoppingCarRepository.findByUserIdAndProductId(data.getUserId(),data.getProductId());
		if(dbList.isEmpty()) {
			ShoppingCar car = new ShoppingCar();
			car.setProductId(data.getProductId());
			car.setUserId(data.getUserId());
			shoppingCarRepository.save(car);
		}
		
		return new ActionResult(true);

	}
}
