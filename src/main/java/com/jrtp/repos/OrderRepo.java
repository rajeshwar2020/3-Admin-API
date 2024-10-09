package com.jrtp.repos;

import com.jrtp.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @Query(value = "SELECT SUM(total_price) FROM orders", nativeQuery = true)
    public Double findTotalAmount();
}
