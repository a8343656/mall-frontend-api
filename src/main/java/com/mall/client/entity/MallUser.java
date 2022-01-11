package com.mall.client.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;




@Entity
@Table(name="mall_user" )
@Data
public class MallUser extends BaseEntity{
	
	@Column(name="name")
	private String name;
	
	@Column(name="address")
	private String address;
	
	@Column(name="account")
	private String account;
	
	@Column(name="pass_word")
	private String passWord;
	
	@Column(name="is_enable")
	private String isEnable;
	
	@Column(name="is_shopable")
	private String isShopable;
	
}
