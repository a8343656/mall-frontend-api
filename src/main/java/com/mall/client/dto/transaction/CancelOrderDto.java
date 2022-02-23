package com.mall.client.dto.transaction;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class CancelOrderDto {

	@Min(1)
	Long userId;
	
	@Min(1)
	Long orderId;
	
}
