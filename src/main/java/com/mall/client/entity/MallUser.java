package com.mall.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;




@Entity
@Table(name="mall_user" )
@Data
@EqualsAndHashCode(callSuper = true)
public class MallUser extends NoVesionEntity{
	
	@Column(name="name")
	private String name;
	
	@Column(name="address")
	private String address;
	
	@Column(name="account")
	private String account;
	
	@Column(name="password")
	private String password;
	
	@Column(name="is_enable")
	private String isEnable;
	
	@Column(name="is_shopable")
	private String isShopable;
	
}
