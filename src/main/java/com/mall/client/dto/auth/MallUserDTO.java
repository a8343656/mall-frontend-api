package com.mall.client.dto.auth;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class MallUserDTO {
	
	@NotEmpty
	public String name;
	
	@NotEmpty
	public String address;

	@NotEmpty
	@Length(min = 2 , message = "長度須在2個字元以上")
	public String account;
	
	@NotEmpty
	public String passWord;
	
}
