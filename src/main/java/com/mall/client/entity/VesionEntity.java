package com.mall.client.entity;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class VesionEntity extends BaseEntity{
	
	@CreatedDate
	private Date createTime;
	
	@Version
	@LastModifiedDate
	private Date updateTime;

	
}
