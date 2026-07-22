package com.tradecore.macrohedge_backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TradeRequest {
    private Long userId;
    private String stockSymbol;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
}