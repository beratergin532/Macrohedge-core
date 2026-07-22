package com.tradecore.macrohedge_backend.repository;

import com.tradecore.macrohedge_backend.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    
    // Borsa sembolüne göre (Örn: THYAO.IS, AAPL) hisse bulma
    Optional<Stock> findBySymbol(String symbol);
}