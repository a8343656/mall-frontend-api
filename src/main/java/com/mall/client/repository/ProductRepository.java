package com.mall.client.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.mall.client.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{
	
	public Page<Product> findByIsBuyable (String isBuyable , Pageable pageable);
	public Product findByIdAndIsBuyableAndAmountGreaterThanEqual (Long id , String isBuyable, Integer amount);
	public Product findByIdAndIsBuyable (Long id , String isBuyable);
	
}
