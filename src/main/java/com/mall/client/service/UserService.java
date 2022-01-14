package com.mall.client.service;


import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mall.client.dto.ActionResult;
import com.mall.client.dto.user.ChangeMemberDataDTO;
import com.mall.client.dto.user.ChangePwsDTO;
import com.mall.client.entity.MallUser;
import com.mall.client.repository.UserRepository;
import com.mall.client.ErrorCode;

@Service
public class UserService {
	
	@Autowired UserRepository userRepository;
	@Autowired UtilService utilService;
	
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
		user.setPassWord(md5Pws);

		userRepository.save(user);
		
		//回傳成功訊息
		return new ActionResult(true);
		
	}
	
	public ActionResult checkOrder (ChangePwsDTO changeData) {
		

		//回傳成功訊息
		return new ActionResult(true);
		
	}

}
