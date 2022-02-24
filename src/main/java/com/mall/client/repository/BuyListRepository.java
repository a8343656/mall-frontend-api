package com.mall.client.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.mall.client.entity.Buylist;

@Repository
public interface BuyListRepository extends JpaRepository<Buylist,Long>{
	
	@Query(value ="select b from Buylist b "
				+ "where b.userId = :userId and (:status is null or :status = b.status) ")
	public Page<Buylist> findByUserIdAndStatus (
			@Param(value = "userId")Long userId ,
			@Param(value = "status")Integer status ,
			Pageable pageable);
	
	public Buylist findByIdAndUserId (Long id , Long userId);
}
