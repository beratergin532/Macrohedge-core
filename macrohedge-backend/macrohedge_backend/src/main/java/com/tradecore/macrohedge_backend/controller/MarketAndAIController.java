package com.tradecore.macrohedge_backend.controller;

import com.tradecore.macrohedge_backend.dto.MacroAIRequest;
import com.tradecore.macrohedge_backend.dto.MacroAIResponse;
import com.tradecore.macrohedge_backend.dto.StockPriceResponse;
import com.tradecore.macrohedge_backend.service.MarketDataAndAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MarketAndAIController {

    private final MarketDataAndAIService marketDataAndAIService;

    @GetMapping("/market/stock/{symbol}")
    public ResponseEntity<StockPriceResponse> getStockPrice(@PathVariable String symbol) {
        return ResponseEntity.ok(marketDataAndAIService.getStockPrice(symbol));
    }

    @PostMapping("/ai/macro-recommendation")
    public ResponseEntity<MacroAIResponse> getMacroRecommendation(@RequestBody MacroAIRequest request) {
        return ResponseEntity.ok(marketDataAndAIService.getMacroRecommendation(request));
    }
}