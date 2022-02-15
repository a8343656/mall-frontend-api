package com.mall.client.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="buylist" )
@Data
@EqualsAndHashCode(callSuper = true)
public class Buylist extends BaseEntity{
	
	@Column(name="user_id")
	private Long userId;
	
	@OneToMany
	@JoinColumn(name="user_buylist_id")
	private Set<BuylistDetail> userBuylistDetail;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="total_price")
	private Integer totalPrice;

}
