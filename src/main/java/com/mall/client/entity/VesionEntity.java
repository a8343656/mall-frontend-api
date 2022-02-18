package com.mall.client.entity;

import java.util.Date;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class VesionEntity extends BaseEntity{
	
	@Version
	@LastModifiedDate
	private Date updateTime;
	
}
