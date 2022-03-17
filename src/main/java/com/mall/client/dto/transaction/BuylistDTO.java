package com.mall.client.dto.transaction;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BuylistDTO {

	@Min(1)
	Long userId;
	
	@NotBlank
	String sendAddress;
	
	@NotBlank
	String paymentMethod;
	
	@Min(0)
	Integer totalPrice;
	
	List<BuylistDetailDTO> detailList;
	
}
