package com.mall.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mall.client.entity.ProductOrder;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder,Long>{
	
}
