package com.mall.client.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import com.mall.client.entity.UserLoginData;

@Repository
public interface UserLoginDataRepository extends JpaRepository<UserLoginData,Long>{
	@Modifying
	@Transactional
	public Integer deleteByUserId(Long long1);
	
	public List<UserLoginData> findByUserIdAndToken(Integer userId , String token);
}
