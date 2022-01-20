package com.mall.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="product_order" )
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductOrder extends BaseEntity{
	
	@Column(name="user_id")
	private Long userId;
	
	@ManyToOne
	@JoinColumn(name="user_id" ,insertable = false, updatable = false)
	private MallUser mallUser;
	
	@Column(name="product_id")
	private Long productId;
	
	@ManyToOne
	@JoinColumn(name="product_id" ,insertable = false, updatable = false)
	private Product product;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="amount")
	private Integer amount;

}
