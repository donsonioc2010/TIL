package com.jong1.cafekiosk.spring.domain.product.repository;

import com.jong1.cafekiosk.spring.domain.product.entity.Product;
import com.jong1.cafekiosk.spring.domain.product.entity.ProductSellingType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // SELECT * FROM PRODUCT WHERE SELLING_TYPE IN (SELLING_TYPES)
    List<Product> findAllBySellingStatusIn(List<ProductSellingType> sellingTypes);

    List<Product> findAllByProductNumberIn(List<String> productNumbers);

    @Query(value = "SELECT p.product_number FROM tbl_product p ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLatestProduct();
}
