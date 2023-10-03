package com.example.ishoporder.repository;

import com.example.ishoporder.model.entity.Order;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM orders WHERE created_at < current_timestamp - INTERVAL '10 minutes' AND is_paid = false", nativeQuery = true)
    void deleteOldOrders();
}
