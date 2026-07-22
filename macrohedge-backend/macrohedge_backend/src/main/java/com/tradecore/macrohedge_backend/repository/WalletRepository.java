package com.tradecore.macrohedge_backend.repository;

import com.tradecore.macrohedge_backend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    
    // Kullanıcı ID'sine göre cüzdanı getirme
    Optional<Wallet> findByUserId(Long userId);
}