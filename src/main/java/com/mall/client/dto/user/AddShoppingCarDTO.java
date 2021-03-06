package com.mall.client.dto.user;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AddShoppingCarDto {

	@Min(1)
	Long userId;
	
	List<ShoppingCarProduct> updateList;
	
}
