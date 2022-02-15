package com.mall.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="buylist_detail" )
@Data
@EqualsAndHashCode(callSuper = true)
public class BuylistDetail extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="user_buylist_id")
	private Buylist userBuylist;
	
	@Column(name="product_id")
	private Long productId;
	
	@ManyToOne
	@JoinColumn(name="product_id" ,insertable = false, updatable = false)
	private Product product;
	
	@Column(name="one_product_price")
	private Integer oneProductPrice;
	
	
	@Column(name="amount")
	private Integer amount;

}
