package com.mall.client.dto.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class ChangeMemberDataDTO {
	
	@Min(1)
	Long id;
	
	@NotEmpty
	String name;

	@NotEmpty
	String address;
	
}
