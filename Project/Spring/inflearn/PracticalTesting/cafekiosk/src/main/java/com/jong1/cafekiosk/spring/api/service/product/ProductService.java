package com.jong1.cafekiosk.spring.api.service.product;

import com.jong1.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingType.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .toList();
    }
}
