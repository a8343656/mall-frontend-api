package com.mall.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="Order" )
@Data
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity{
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="product_id")
	private Long productId;
	
	@Column(name="status")
	private Integer status;

}
