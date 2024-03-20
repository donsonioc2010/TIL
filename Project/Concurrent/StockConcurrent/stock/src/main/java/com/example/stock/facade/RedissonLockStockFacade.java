package com.example.stock.facade;

import com.example.stock.service.StockService;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
public class RedissonLockStockFacade {
    private RedissonClient redissonClient;

    private StockService stockService;

    public RedissonLockStockFacade(RedissonClient redissonClient, StockService stockService) {
        this.redissonClient = redissonClient;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) {
        RLock lock = redissonClient.getLock(id.toString());
        try {
            //몇초동안, 몇초동안 점유할 것인지
            boolean availasble = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if(!availasble) {
                throw new RuntimeException("Lock을 획득하지 못했습니다.");
            }

            stockService.decrease(id, quantity);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        finally {
            lock.unlock();
        }
    }
}
