package com.mall.client.dto.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class GetOrderListDTO {
	
	@Min(1)
	Long userId;
	
	@Min(0)
	Integer page ;
	
	@Min(0)
	Integer pageSize ;
	
	@NotEmpty
	String sortCol;
	
	@NotEmpty
	String sortOrder;
	
}
