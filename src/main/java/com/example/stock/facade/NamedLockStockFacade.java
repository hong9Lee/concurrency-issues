package com.example.stock.facade;

import com.example.stock.repository.LockRepository;
import com.example.stock.service.OptimisticLockStockService;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final StockService stockService;

    public NamedLockStockFacade(LockRepository lockRepository, StockService service) {
        this.lockRepository = lockRepository;
        this.stockService = service;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        try {
            lockRepository.getLock(id.toString());
            stockService.decrease(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}
