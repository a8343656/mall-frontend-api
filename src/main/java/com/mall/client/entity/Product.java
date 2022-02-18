package com.mall.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="product" )
@Data
@EqualsAndHashCode(callSuper = true)
public class Product extends VesionEntity{
	
	@Column(name="name")
	private String name;
	
	@Column(name="amount")
	private Integer amount;
	
	@Column(name="price")
	private Integer price;
	
	@Column(name="describe")
	private String describe;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="is_buyable")
	private String isBuyable;
	
	

}
