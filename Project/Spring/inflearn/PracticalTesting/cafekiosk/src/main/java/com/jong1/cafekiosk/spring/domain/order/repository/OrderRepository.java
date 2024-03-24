package com.jong1.cafekiosk.spring.domain.order.repository;

import com.jong1.cafekiosk.spring.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
