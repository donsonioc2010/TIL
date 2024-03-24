package com.jong1.cafekiosk.spring.api.service.order;

import com.jong1.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import com.jong1.cafekiosk.spring.api.service.order.response.OrderResponse;
import com.jong1.cafekiosk.spring.domain.order.entity.Order;
import com.jong1.cafekiosk.spring.domain.order.repository.OrderRepository;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.repository.ProductRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumbers = request.getProductNumbers();

        List<Product> products = findProductsBy(productNumbers);

        // Order
        Order order = Order.create(products, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private List<Product> findProductsBy(List<String> productNumbers) {
        // Product
        List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

        Map<String, Product> productMap = products.stream()
            .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumbers.stream()
            .map(productMap::get)
            .toList();
    }
}
