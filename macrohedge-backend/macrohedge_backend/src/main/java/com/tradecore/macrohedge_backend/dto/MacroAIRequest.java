package com.tradecore.macrohedge_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MacroAIRequest {
    private String scenario_description;
    private Integer investment_horizon_years;
    private String risk_tolerance;
}