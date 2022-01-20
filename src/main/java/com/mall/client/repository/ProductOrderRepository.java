package com.mall.client.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mall.client.entity.ProductOrder;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder,Long>{
	
	public Page<ProductOrder> findByUserId (Long userId,Pageable pageable);
}
