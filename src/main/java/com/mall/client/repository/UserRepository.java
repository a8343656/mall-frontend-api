package com.mall.client.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mall.client.entity.MallUser;

@Repository
public interface UserRepository extends JpaRepository<MallUser,Long>{
	
	public List<MallUser> findByAccount(String account);
	
}
