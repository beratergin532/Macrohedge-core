package com.tradecore.macrohedge_backend.repository;

import com.tradecore.macrohedge_backend.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    
    // Kullanıcının sahip olduğu tüm hisse portföyü
    List<Portfolio> findByUserId(Long userId);
    
    // Kullanıcının belirli bir hisseden elinde ne kadar olduğunu bulma
    Optional<Portfolio> findByUserIdAndStockId(Long userId, Long stockId);
}