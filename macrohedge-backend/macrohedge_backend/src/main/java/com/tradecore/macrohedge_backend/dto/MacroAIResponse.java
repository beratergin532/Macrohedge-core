package com.tradecore.macrohedge_backend.dto;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class MacroAIResponse {
    private List<String> impacted_sectors;
    private Map<String, Integer> portfolio_allocation_percent;
    private List<String> suggested_stock_symbols;
    private Integer risk_score_out_of_10;
    private String executive_summary;
}