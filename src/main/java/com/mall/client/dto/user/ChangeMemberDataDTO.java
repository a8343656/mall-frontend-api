package com.mall.client.dto.user;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import lombok.Data;

@Data
public class ChangeMemberDataDTO {
	
	@Min(1)
	public Long id;
	
	@NotEmpty
	public String name;

	@NotEmpty
	public String address;
	
}
