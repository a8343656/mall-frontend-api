package com.mall.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.mall.client.dto.ActionResult;
import com.mall.client.dto.user.AddShoppingCarDto;
import com.mall.client.dto.user.ChangePwsDTO;
import com.mall.client.dto.user.ChangeUserDataDTO;
import com.mall.client.dto.user.GetOrderListDTO;
import com.mall.client.dto.user.GetUserDataDto;
import com.mall.client.dto.user.ShoppingCarProduct;
import com.mall.client.dto.user.updateShoppingCarDto;
import com.mall.client.entity.Buylist;
import com.mall.client.entity.MallUser;
import com.mall.client.entity.ShoppingCar;
import com.mall.client.repository.BuyListRepository;
import com.mall.client.repository.ShoppingCarRepository;
import com.mall.client.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {
	@Autowired UserService userService;
	@MockBean UserRepository userRepository;
	@MockBean BuyListRepository buyListRepository;
	@MockBean CheckService checkService;
	@MockBean ShoppingCarRepository shoppingCarRepository;
	
	// 成功取得使用者資料
	@Test
	void testGetUserDataSuccess() {
		
		MallUser dbUser = new MallUser();
		dbUser.setName("test");
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbUser));


		GetUserDataDto dto = new GetUserDataDto();
		dto.setUserId(1L);
		
		
		ActionResult result =userService.getUserData(dto);
		Assertions.assertNotNull(result.getData());
		MallUser userData = (MallUser) result.getData();
		Assertions.assertEquals(userData.getName(),"test","user name not correct");
		
	}
	
	
	// 成功修改使用者資料
	@Test
	void testChangeUserDataSuccess() {
		MallUser dbUser = new MallUser();
		Mockito.when(userRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(dbUser));
		
		ChangeUserDataDTO dto = new ChangeUserDataDTO();
		dto.setUserId(1L);
		dto.setAddress("test address");
		dto.setCellPhone("phone");
		dto.setName("name");
		
		ActionResult result =userService.changeUserData(dto);
		Assertions.assertNull(result.getErrorCode());
		Assertions.assertTrue(result.isSuccess());
		
	}
	
	// 成功修改密碼
	@Test
	void testChangePwsSuccess() {
		
		MallUser dbUser = new MallUser();
		Mockito.when(userRepository.findById(Mockito.any())).thenReturn(Optional.of(dbUser));

		ChangePwsDTO dto = new ChangePwsDTO();
		dto.setPassword("pws");
		
		ActionResult result =userService.changePws(dto);
		Assertions.assertNull(result.getErrorCode());
		Assertions.assertTrue(result.isSuccess());
		
	}
	
	// 成功取得購物清單
	@Test
	void testGetBuylistSuccess() {
		
		Buylist dbBuylist1 = new Buylist();
		dbBuylist1.setStatus(1);
		List<Buylist> buylistArray = new ArrayList<>();
		buylistArray.add(dbBuylist1);
		Mockito.when(buyListRepository.findByUserIdAndStatus(Mockito.any(),Mockito.any(),Mockito.any()))
		.thenReturn(new PageImpl<>(buylistArray));

		GetOrderListDTO dto = new GetOrderListDTO();
		dto.setUserId(1L);
		PageRequest pageRequest = PageRequest.of(1, 1, Sort.by("test").descending());
		ActionResult result =userService.getBuylist(dto,pageRequest);

		Assertions.assertNotNull(result.getData(), "data should not null");
		@SuppressWarnings("unchecked")
		Page<Buylist> data = (Page<Buylist>)result.getData();
		Assertions.assertEquals(data.getContent().get(0).getStatus(),1,"status not correct");
		
	}
	
	// 新的商品加入購物車
	@Test
	void testAddShoppingCarAddNewProduct() {
		
		Mockito.when(checkService.isUserPresentAndBuyAble(Mockito.any())).thenReturn(new ActionResult(true));
		Mockito.when(shoppingCarRepository.findByUserIdAndProductId(Mockito.any(),Mockito.eq(1L)))
		.thenReturn(new ArrayList<ShoppingCar>());

		List<ShoppingCarProduct> addList = new ArrayList<>();
		ShoppingCarProduct addProdcut = new ShoppingCarProduct();
		addProdcut.setProductId(1L);
		addList.add(addProdcut);
		
		AddShoppingCarDto dto = new AddShoppingCarDto();
		dto.setUpdateList(addList);
		
		ActionResult result = userService.addShoppingCar(dto);
		Assertions.assertTrue(result.isSuccess());
	}
	// 重複商品加入購物車
	@Test
	void testAddShoppingCarAddSameProduct() {
		
		ShoppingCar dbData = new ShoppingCar();
		dbData.setAmount(1);
		List<ShoppingCar> dbList = new ArrayList<>();
		dbList.add(dbData);
		
		Mockito.when(checkService.isUserPresentAndBuyAble(Mockito.any())).thenReturn(new ActionResult(true));
		Mockito.when(shoppingCarRepository.findByUserIdAndProductId(Mockito.any(),Mockito.eq(1L)))
		.thenReturn(dbList);

		ShoppingCarProduct addProdcut = new ShoppingCarProduct();
		addProdcut.setProductId(1L);
		addProdcut.setSaveAmount(2);
		List<ShoppingCarProduct> addList = new ArrayList<>();
		addList.add(addProdcut);
		AddShoppingCarDto dto = new AddShoppingCarDto();
		dto.setUpdateList(addList);
		
		ActionResult result = userService.addShoppingCar(dto);
		Assertions.assertTrue(result.isSuccess());
	}
	
	// 成功從購物車中刪除商品
	@Test
	void testUpdateShoppingCarDeleteProduct() {
		
		Mockito.when(checkService.isUserPresentAndBuyAble(Mockito.any())).thenReturn(new ActionResult(true));

		ShoppingCarProduct updateItme = new ShoppingCarProduct();
		updateItme.setProductId(1L);
		List<ShoppingCarProduct> updateList= new ArrayList<>();
		updateList.add(updateItme);
		updateShoppingCarDto dto = new updateShoppingCarDto();
		
		dto.setAction("delete");
		dto.setUpdateList(updateList);
		
		ActionResult result = userService.updateShoppingCar(dto);
		Assertions.assertTrue(result.isSuccess());
	}
	
	
	// 成功更改購物車中的數量
	@Test
	void testUpdateShoppingCarChangeAmount() {
		
		ShoppingCar dbData = new ShoppingCar();
		dbData.setAmount(1);
		List<ShoppingCar> dataList = new ArrayList<>();
		dataList.add(dbData);
		
		Mockito.when(shoppingCarRepository.findByUserIdAndProductId(Mockito.any(),Mockito.any())).thenReturn(dataList);
		Mockito.when(checkService.isUserPresentAndBuyAble(Mockito.any())).thenReturn(new ActionResult(true));

		ShoppingCarProduct updateItme = new ShoppingCarProduct();
		updateItme.setSaveAmount(2);
		updateItme.setProductId(1L);
		
		List<ShoppingCarProduct> updateList= new ArrayList<>();
		updateList.add(updateItme);
		
		updateShoppingCarDto dto = new updateShoppingCarDto();
		dto.setAction("update");
		dto.setUpdateList(updateList);
		
		ActionResult result = userService.updateShoppingCar(dto);
		Assertions.assertTrue(result.isSuccess());
	}
	
	// 成功取得使用者購物車清單
	@Test
	void testGetShoppingCarListSuccess() {
		
		ShoppingCar dbData = new ShoppingCar();
		dbData.setProductId(1L);
		List<ShoppingCar> dbDataList = new ArrayList<>();
		dbDataList.add(dbData);
		
		Mockito.when(shoppingCarRepository.findByUserId(Mockito.any(),Mockito.any())).thenReturn(new PageImpl<>(dbDataList));
		
		PageRequest pageRequest = PageRequest.of(1, 1, Sort.by("test").descending());
		ActionResult result = userService.getShoppingCarList(1L,pageRequest);
		
		Assertions.assertNotNull(result.getData());
		@SuppressWarnings("unchecked")
		Page<ShoppingCar> data = (Page<ShoppingCar>)result.getData();
		Assertions.assertEquals(data.getContent().get(0).getProductId(), 1L);
		
	}
}
