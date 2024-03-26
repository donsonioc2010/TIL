package com.jong1.cafekiosk.spring.api.service.product;

import com.jong1.cafekiosk.spring.api.controller.product.request.ProductCreateRequest;
import com.jong1.cafekiosk.spring.api.service.product.request.ProductCreateServiceRequest;
import com.jong1.cafekiosk.spring.api.service.product.response.ProductResponse;
import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import com.jong1.cafekiosk.spring.domain.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 동시성 이슈 가능은 하나, 학습용이니까 ㅎ;
    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {
        // ProductNumber [001, 002, 003~~~]
        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product saveProduct = productRepository.save(product);

        return ProductResponse.of(saveProduct);
    }


    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingType.forDisplay());

        return products.stream()
            .map(ProductResponse::of)
            .toList();
    }

    private String createNextProductNumber() {
        // DB에서 마지막에 저장된 Product의 상품번호를 읽은 이후, +1
        String latestProductNumber = productRepository.findLatestProduct();

        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        // 9 -> 009, 10 -> 010
        return String.format("%03d", nextProductNumberInt);
    }

}
