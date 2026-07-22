package com.tradecore.macrohedge_backend.service;

import com.tradecore.macrohedge_backend.dto.TradeRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TradingService {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Hisse Alım İşlemi (MS SQL Server sp_BuyStock Stored Procedure Tetikler)
     */
    @Transactional
    public void buyStock(TradeRequest request) {
        entityManager.createNativeQuery(
            "EXEC sp_BuyStock @p_user_id = :userId, @p_symbol = :symbol, @p_quantity = :quantity, @p_unit_price = :unitPrice"
        )
        .setParameter("userId", request.getUserId())
        .setParameter("symbol", request.getStockSymbol())
        .setParameter("quantity", request.getQuantity())
        .setParameter("unitPrice", request.getUnitPrice())
        .executeUpdate();
    }

    /**
     * Hisse Satım İşlemi (MS SQL Server sp_SellStock Stored Procedure Tetikler)
     */
    @Transactional
    public void sellStock(TradeRequest request) {
        entityManager.createNativeQuery(
            "EXEC sp_SellStock @p_user_id = :userId, @p_symbol = :symbol, @p_quantity = :quantity, @p_unit_price = :unitPrice"
        )
        .setParameter("userId", request.getUserId())
        .setParameter("symbol", request.getStockSymbol())
        .setParameter("quantity", request.getQuantity())
        .setParameter("unitPrice", request.getUnitPrice())
        .executeUpdate();
    }
}