package com.example.stock.service;

import static org.junit.jupiter.api.Assertions.*;
import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceTest {
    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    public void before() {
        stockRepository.saveAndFlush(new Stock(1L, 100L));
    }

    @AfterEach
    public void after() {
        stockRepository.deleteById(1L);
    }

    @Test
    public void 재고감소() {
        stockService.decrease(1L, 1L);

        stockRepository.findById(1L).ifPresent(stock -> {
            assertEquals(99L, stock.getQuantity());
        });
    }
}