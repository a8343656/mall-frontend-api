package com.mall.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mall.client.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
	
}
