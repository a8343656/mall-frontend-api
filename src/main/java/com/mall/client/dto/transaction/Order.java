package com.mall.client.dto.transaction;

import java.util.List;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class Order {

	Long productId;
	
	Integer amount;
	
}
