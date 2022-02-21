package com.mall.client.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="buylist_detail" )
@Setter
@Getter
@EqualsAndHashCode(callSuper = true )  
public class BuylistDetail extends NoVesionEntity{
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="buylist_id")
	private Buylist userBuylist;
	
	@Column(name="product_id")
	private Long productId;
	
	@OneToOne
	@JoinColumn(name="product_id" ,insertable = false, updatable = false)
	private Product product;
	
	@Column(name="one_product_price")
	private Integer oneProductPrice;
	
	
	@Column(name="amount")
	private Integer amount;

}
