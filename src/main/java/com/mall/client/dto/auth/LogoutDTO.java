package com.mall.client.dto.auth;

import javax.validation.constraints.Min;
import lombok.Data;

@Data
public class LogoutDTO {

	@Min(0)
	Long userId;
	
}
