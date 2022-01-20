package com.mall.client.dto.transaction;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class BuyDTO {

	@Min(1)
	Long userId;
	
	@Min(1)
	Long productId;
	
	@Min(1)
	Integer amount;
	
}
