package com.jong1.cafekiosk.spring.domain.order.repository;

import com.jong1.cafekiosk.spring.domain.order.entity.Order;
import com.jong1.cafekiosk.spring.domain.order.entity.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o "
        + "where o.registeredDateTime >= :startDateTime "
        + " and o.registeredDateTime < :endDateTime "
        + " and o.orderStatus = :orderStatus")
    List<Order> findOrderBy(LocalDateTime startDateTime, LocalDateTime endDateTime, OrderStatus orderStatus);
}
