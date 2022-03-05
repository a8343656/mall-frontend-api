package com.mall.client.dto.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangePwsDTO {
	
	@Min(1)
	Long userId;
	
	@NotBlank
	String password;

}
