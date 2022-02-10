package com.mall.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mall.client.entity.ShoppingCar;

@Repository
public interface ShoppingCarRepository extends JpaRepository<ShoppingCar,Long>{
	
	List<ShoppingCar> findByUserIdAndProductId (Long userId , Long productId);
	
	Page<ShoppingCar> findByUserId (Long userId,Pageable pageable);
	
	@Modifying
	@Transactional
	@Query(value ="delete from ShoppingCar s where s.userId = :userId and productId in :productIdList ")
	void deleteByUserIdAndProductIds(
			@Param(value = "userId") Long userId , 
			@Param(value = "productIdList")List<Long>productIdList);
}
