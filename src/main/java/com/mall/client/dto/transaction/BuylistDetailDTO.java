package com.mall.client.dto.transaction;


import javax.validation.constraints.Min;

import lombok.Data;

@Data
public class BuylistDetailDTO {

	Long productId;
	
	@Min(0)
	Integer amount;
	
	@Min(0)
	Integer oneProductPrice;
	
}
