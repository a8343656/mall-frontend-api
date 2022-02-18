package com.mall.client.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="buylist" )
@Setter
@Getter
@EqualsAndHashCode(callSuper = true , onlyExplicitlyIncluded = true)
public class Buylist extends NoVesionEntity{
	
	@Column(name="user_id")
	private Long userId;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="buylist_id")
	private List<BuylistDetail> buylistDetail;
	
	@Column(name="status")
	private Integer status;
	
	@Column(name="total_price")
	private Integer totalPrice;

}
