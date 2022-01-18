package com.mall.client.dto.product;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class AddShoppingCarDTO {

	@Min(1)
	Long userId;
	
	@Min(1)
	Long productId;
	
}
