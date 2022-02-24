package com.mall.client.service;


import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.user.AddShoppingCarDTO;
import com.mall.client.dto.user.ChangePwsDTO;
import com.mall.client.dto.user.ChangeUserDataDTO;
import com.mall.client.dto.user.GetOrderListDTO;
import com.mall.client.dto.user.GetShoppingCarDTO;
import com.mall.client.dto.user.GetUserDataDto;
import com.mall.client.dto.user.RemoveShoppingCarDTO;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.Buylist;
import com.mall.client.entity.BuylistDetail;
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
	
	public ActionResult addToShoppingCar (AddShoppingCarDTO data) {
		
		// 檢查該使用者是否存在，並確認是否有購買資格
		ActionResult checkResult = checkService.isUserPresentAndBuyAble(data.getUserId());
		if (!checkResult.isSuccess()) {
			return checkResult;
		}
			
		// 查詢該商品是否可被購買，且庫存大於1
		try {
			checkService.isProductBuyable(data.getProductId(),1);
		}catch(CantBuyException ex) {
			throw ex;
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
	
	public ActionResult getShoppingCarList (Long userId , Pageable pageable) {
		
		Page<ShoppingCar> dbData = shoppingCarRepository.findByUserId(userId,pageable);
		return new ActionResult(true ,dbData);
		
	}
	
	public ActionResult removeFromShoppingCar (RemoveShoppingCarDTO data) {
		System.out.println(data.getProductIdList());
		shoppingCarRepository.deleteByUserIdAndProductIds(data.getUserId(), data.getProductIdList());
		return new ActionResult(true);
		
	}


	

}
