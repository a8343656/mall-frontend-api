package com.mall.client.dto.user;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class ShoppingCarProduct {
	@Min(1)
	Long productId;
	
	Integer saveAmount;
}
