package com.mall.client.dto.transaction;


import lombok.Data;

@Data
public class BuyProduct {

	Long productId;
	
	Integer buyAmount;
	
	Integer oneProductPrice;
	
}
