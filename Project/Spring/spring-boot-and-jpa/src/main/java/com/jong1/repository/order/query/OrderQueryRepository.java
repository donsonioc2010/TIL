package com.jong1.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    public List<OrderQuerytDto> findOrderQueryDtos() {
        List<OrderQuerytDto> result = findOrders();
        result.forEach(o -> o.setOrderItems(findOrderItems(o.getOrderId())));
        return result;
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery(
                        "select new com.jong1.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count )  from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id = :orderId", OrderItemQueryDto.class
                ).setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQuerytDto> findOrders() {
        return em.createQuery(
                "select new com.jong1.repository.order.query.OrderQuerytDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o " +
                        "join o.member m " +
                        "join o.delivery d", OrderQuerytDto.class
        ).getResultList();
    }
}
