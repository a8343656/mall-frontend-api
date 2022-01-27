package com.mall.client.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.user.ChangeMemberDataDTO;
import com.mall.client.dto.user.ChangePwsDTO;
import com.mall.client.dto.user.GetShoppingCarDTO;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.ProductOrder;
import com.mall.client.entity.ShoppingCar;
import com.mall.client.repository.ProductOrderRepository;
import com.mall.client.repository.ShoppingCarRepository;
import com.mall.client.repository.UserRepository;
import com.mall.client.ErrorCode;

@Service
public class UserService {
	
	@Autowired UserRepository userRepository;
	@Autowired UtilService utilService;
	@Autowired ShoppingCarRepository shoppingCarRepository;
	@Autowired ProductOrderRepository productOrderRepository;
	
	
	public ActionResult changeMemberData (ChangeMemberDataDTO changeData) {
		
		// 查詢該使用者是否存在
		Optional<MallUser> data = userRepository.findById(changeData.getId());
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
		Optional<MallUser> data = userRepository.findById(changeData.getId());
		if(!data.isPresent()) {
			return new ActionResult(false , ErrorCode.USER_ID_NOT_FOUND.getCode(),ErrorCode.USER_ID_NOT_FOUND.getMsg());
		}
		
		// 依照傳入的資料修改並儲存
		MallUser user = data.get();
		String md5Pws = utilService.getMD5(changeData.getPassWord());
		user.setPassword(md5Pws);

		userRepository.save(user);
		
		//回傳成功訊息
		return new ActionResult(true);
		
	}
	
	public ActionResult getOrderList (Long userId , Pageable pageable) {
		
		Page<ProductOrder> dbData = productOrderRepository.findByUserId(userId,pageable);
		//回傳成功訊息
		return new ActionResult(true,dbData);
		
	}
	
	public ActionResult getShoppingCarList (Long userId , Pageable pageable) {
		
		Page<ShoppingCar> dbData = shoppingCarRepository.findByUserId(userId,pageable);
		return new ActionResult(true ,dbData);
		
	}
	

}
