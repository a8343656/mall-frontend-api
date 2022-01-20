package com.mall.client.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mall.client.entity.ShoppingCar;

@Repository
public interface ShoppingCarRepository extends JpaRepository<ShoppingCar,Long>{
	
	public List<ShoppingCar> findByUserIdAndProductId (Long userId , Long productId);
	
	public Page<ShoppingCar> findByUserId (Long userId,Pageable pageable);
}