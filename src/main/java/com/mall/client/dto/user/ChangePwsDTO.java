package com.mall.client.dto.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class ChangePwsDTO {
	
	@Min(1)
	Long userId;
	
	@NotEmpty
	String password;

}
