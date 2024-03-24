package com.jong1.cafekiosk.spring.domain.orderproduct.repository;

import com.jong1.cafekiosk.spring.domain.orderproduct.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
