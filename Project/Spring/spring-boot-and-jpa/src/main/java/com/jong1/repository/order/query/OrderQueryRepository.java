package com.jong1.repository.order.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager em;

    // 결국 Order를기반으로 N+1이 된다
    public List<OrderQuerytDto> findOrderQueryDtos() {
        List<OrderQuerytDto> result = findOrders();
        result.forEach(o -> o.setOrderItems(findOrderItems(o.getOrderId())));
        return result;
    }

    // 1+N -> 1+1
    public List<OrderQuerytDto> findAllByDto_optimization() {
        List<OrderQuerytDto> result = findOrders();
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new com.jong1.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count )  from OrderItem oi " +
                                "join oi.item i " +
                                "where oi.order.id in :orderIds", OrderItemQueryDto.class
                ).setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    private List<Long> toOrderIds(List<OrderQuerytDto> result) {
        return result.stream()
                .map(OrderQuerytDto::getOrderId)
                .toList();
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
