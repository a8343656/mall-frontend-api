package com.mall.client.dto.auth;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginDTO {

	@NotEmpty
	String account;
	
	@NotEmpty
	String passWord;
	
}
