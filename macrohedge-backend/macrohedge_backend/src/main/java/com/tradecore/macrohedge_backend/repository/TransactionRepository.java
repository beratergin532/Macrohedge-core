package com.tradecore.macrohedge_backend.repository;

import com.tradecore.macrohedge_backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    // Kullanıcının geçmiş alım-satım işlem geçmişini listeleme
    List<Transaction> findByUserIdOrderByExecutedAtDesc(Long userId);
}