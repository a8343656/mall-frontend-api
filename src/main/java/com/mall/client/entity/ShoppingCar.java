package com.mall.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="shopping_car" )
@Data
@EqualsAndHashCode(callSuper = true)
public class ShoppingCar extends BaseEntity{
	
	@Column(name="user_id")
	private Long userId;
	
	@Column(name="product_id")
	private Long productId;

}
