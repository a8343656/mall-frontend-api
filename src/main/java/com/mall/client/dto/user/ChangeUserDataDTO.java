package com.mall.client.dto.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeUserDataDTO {
	
	@Min(1)
	Long userId;
	
	@NotBlank
	String name;

	@NotBlank
	String address;
	
	@Pattern(regexp = "^09\\d{2}\\d{6}$" , message ="手機號碼須為10碼，且為09開頭")
	String cellPhone;
	
}
