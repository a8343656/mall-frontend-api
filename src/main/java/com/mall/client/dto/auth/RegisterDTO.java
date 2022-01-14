package com.mall.client.dto.auth;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class RegisterDTO {
	
	@NotEmpty
	String name;
	
	@NotEmpty
	String address;

	@NotEmpty
	@Length(min = 2 , message = "長度須在2個字元以上")
	String account;
	
	@NotEmpty
	String passWord;
	
}
