package com.mall.client.dto.auth;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {

	@NotEmpty
	public String account;
	
	@NotEmpty
	public String passWord;
	
}
