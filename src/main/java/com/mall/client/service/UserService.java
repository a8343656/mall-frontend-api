package com.mall.client.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.user.ChangePwsDTO;
import com.mall.client.dto.user.ChangeUserDataDTO;
import com.mall.client.dto.user.GetOrderListDTO;
import com.mall.client.dto.user.GetUserDataDto;
import com.mall.client.dto.user.RemoveShoppingCarDTO;
import com.mall.client.dto.user.AddShoppingCarDto;
import com.mall.client.dto.user.ShoppingCarProduct;
import com.mall.client.dto.user.updateShoppingCarDto;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.Buylist;
import com.mall.client.entity.ShoppingCar;
import com.mall.client.exception.CantBuyException;
import com.mall.client.repository.BuyListRepository;
import com.mall.client.repository.ShoppingCarRepository;
import com.mall.client.repository.UserRepository;
import com.mall.client.ErrorCode;

@Service
public class UserService {
	
	@Autowired UserRepository userRepository;
	@Autowired UtilService utilService;
	@Autowired CheckService checkService;
	@Autowired ShoppingCarRepository shoppingCarRepository;
	@Autowired BuyListRepository buyListRepository;
	
	
public ActionResult getUserData (GetUserDataDto data) {
		
		// 查詢該使用者是否存在
		Optional<MallUser> userData = userRepository.findById(data.getUserId());
		if(!userData.isPresent()) {
			return new ActionResult(false , ErrorCode.USER_ID_NOT_FOUND.getCode(),ErrorCode.USER_ID_NOT_FOUND.getMsg());
		}
		
		// 依照傳入的資料修改並儲存
		MallUser user = userData.get();
		
		//回傳成功訊息
		return new ActionResult(true,user);
		
	}
	
	public ActionResult changeUserData (ChangeUserDataDTO changeData) {
		
		// 查詢該使用者是否存在
		Optional<MallUser> data = userRepository.findById(changeData.getUserId());
		if(!data.isPresent()) {
			return new ActionResult(false , ErrorCode.USER_ID_NOT_FOUND.getCode(),ErrorCode.USER_ID_NOT_FOUND.getMsg());
		}
		
		// 依照傳入的資料修改並儲存
		MallUser user = data.get();
		user.setName(changeData.getName());
		user.setAddress(changeData.getAddress());
		userRepository.save(user);
		
		//回傳成功訊息
		return new ActionResult(true);
		
	}
	
	public ActionResult changePws (ChangePwsDTO changeData) {
		
		// 查詢該使用者是否存在
		Optional<MallUser> data = userRepository.findById(changeData.getUserId());
		if(!data.isPresent()) {
			return new ActionResult(false , ErrorCode.USER_ID_NOT_FOUND.getCode(),ErrorCode.USER_ID_NOT_FOUND.getMsg());
		}
		
		// 依照傳入的資料修改並儲存
		MallUser user = data.get();
		String md5Pws = utilService.getMD5(changeData.getPassword());
		user.setPassword(md5Pws);

		userRepository.save(user);
		
		//回傳成功訊息
		return new ActionResult(true);
		
	}
	
	public ActionResult getBuylist (GetOrderListDTO data , Pageable pageable) {
		
		Page<Buylist> dbData = buyListRepository.findByUserIdAndStatus(data.getUserId(),data.getStatus(),pageable);
		//回傳成功訊息
		return new ActionResult(true,dbData);
		
	}
	
	public ActionResult addShoppingCar (AddShoppingCarDto data) {
		// 檢查該使用者是否存在，並確認是否有購買資格
		ActionResult checkResult = checkService.isUserPresentAndBuyAble(data.getUserId());
		if (!checkResult.isSuccess()) {
			return checkResult;
		}
		for(ShoppingCarProduct addItem : data.getUpdateList()) {
			// 查詢該商品是否可被購買，且庫存大於想購買的數量
			try {
				checkService.isProductBuyable(addItem.getProductId(),addItem.getSaveAmount());
			}catch(CantBuyException ex) {
				throw ex;
			}
			
			// 判斷該商品已在購物車中，若已存在原始購買數量 + 新增購買數量
			List<ShoppingCar> dbList = shoppingCarRepository.findByUserIdAndProductId(data.getUserId(),addItem.getProductId());
			if(dbList.isEmpty()) {
				ShoppingCar newCar = new ShoppingCar();
				newCar.setProductId(addItem.getProductId());
				newCar.setAmount(addItem.getSaveAmount());
				newCar.setUserId(data.getUserId());
				shoppingCarRepository.save(newCar);
			} else {
				ShoppingCar dbCar = dbList.get(0);
				dbCar.setAmount( dbCar.getAmount() + addItem.getSaveAmount());
				shoppingCarRepository.save(dbCar);
			}
		}
		return new ActionResult(true);
		
	}
	
	public ActionResult updateShoppingCar (updateShoppingCarDto data) {
		
		// 檢查該使用者是否存在，並確認是否有購買資格
		ActionResult checkResult = checkService.isUserPresentAndBuyAble(data.getUserId());
		if (!checkResult.isSuccess()) {
			return checkResult;
		}
		
		// 如果動作為刪除
		if(data.getAction().equals("delete")) {
			List<Long> deleteIdList = new ArrayList<>();
			for(ShoppingCarProduct item : data.getUpdateList()) {
				deleteIdList.add(item.getProductId());
			}
			shoppingCarRepository.deleteByUserIdAndProductIds(data.getUserId(), deleteIdList);
		} else {
			//其餘動作為更新
			for(ShoppingCarProduct updateItem : data.getUpdateList()) {
				
				// 由於不是真正的購買，且前端會重Load，購買商品不做檢查直接更新數量
				List<ShoppingCar> dbList = shoppingCarRepository.findByUserIdAndProductId(data.getUserId(),updateItem.getProductId());
				ShoppingCar dbCar = dbList.get(0);
				dbCar.setAmount(updateItem.getSaveAmount());
				shoppingCarRepository.save(dbCar);
				
			}
		}
		return new ActionResult(true);

	}
	
	public ActionResult getShoppingCarList (Long userId , Pageable pageable) {
		
		Page<ShoppingCar> dbData = shoppingCarRepository.findByUserId(userId,pageable);
		return new ActionResult(true ,dbData);
		
	}
	
	public ActionResult removeFromShoppingCar (RemoveShoppingCarDTO data) {
		shoppingCarRepository.deleteByUserIdAndProductIds(data.getUserId(), data.getProductIdList());
		return new ActionResult(true);
		
	}


	

}
