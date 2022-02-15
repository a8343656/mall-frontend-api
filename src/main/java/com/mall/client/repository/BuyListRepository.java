package com.mall.client.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mall.client.entity.Buylist;

@Repository
public interface BuyListRepository extends JpaRepository<Buylist,Long>{
	
	public Page<Buylist> findByUserId (Long userId,Pageable pageable);
}
