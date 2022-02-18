package com.mall.client.dto.transaction;

import java.util.List;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class BuylistDTO {

	@Min(1)
	Long userId;
	
	@Min(0)
	Integer totalPrice;
	
	List<BuylistDetailDTO> buyList;
	
}
