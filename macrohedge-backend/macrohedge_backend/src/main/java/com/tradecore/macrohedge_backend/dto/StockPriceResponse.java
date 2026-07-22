package com.tradecore.macrohedge_backend.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class StockPriceResponse {
    private String symbol;
    private BigDecimal price;
    private String currency;
}