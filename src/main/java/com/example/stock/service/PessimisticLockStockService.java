package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PessimisticLockStockService {

    private final StockRepository stockRepository;

    public PessimisticLockStockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {

        // Stock 조회
        // 재고를 감소시킨 뒤, 갱신된 값을 저장한다.
        Stock stock = stockRepository.findByIdWithPessimisticLock(id);
        stock.decrease(quantity);

        stockRepository.save(stock);
    }
}
