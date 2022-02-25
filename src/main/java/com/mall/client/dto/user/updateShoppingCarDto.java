package com.mall.client.dto.user;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class updateShoppingCarDto {

	@Min(1)
	Long userId;
	
	@NotEmpty
	String action;
	
	List<ShoppingCarProduct> updateList;
	
}
