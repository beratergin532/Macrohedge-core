package com.tradecore.macrohedge_backend.controller;

import com.tradecore.macrohedge_backend.dto.MacroAIRequest;
import com.tradecore.macrohedge_backend.dto.MacroAIResponse;
import com.tradecore.macrohedge_backend.dto.StockHistoryItem;
import com.tradecore.macrohedge_backend.dto.StockPriceResponse;
import com.tradecore.macrohedge_backend.service.MarketDataAndAIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MarketAndAIController {

    private final MarketDataAndAIService marketDataAndAIService;

    @GetMapping("/market/stock/{symbol}")
    public ResponseEntity<StockPriceResponse> getStockPrice(@PathVariable String symbol) {
        return ResponseEntity.ok(marketDataAndAIService.getStockPrice(symbol));
    }

    @GetMapping("/market/stock/{symbol}/history")
    public ResponseEntity<List<StockHistoryItem>> getStockHistory(
            @PathVariable String symbol,
            @RequestParam(defaultValue = "1mo") String period) {
        return ResponseEntity.ok(marketDataAndAIService.getStockHistory(symbol, period));
    }

    @PostMapping("/ai/macro-recommendation")
    public ResponseEntity<MacroAIResponse> getMacroRecommendation(@RequestBody MacroAIRequest request) {
        return ResponseEntity.ok(marketDataAndAIService.getMacroRecommendation(request));
    }

    @GetMapping("/market/news")
    public ResponseEntity<?> getMarketNews() {
        return ResponseEntity.ok(marketDataAndAIService.getMarketNews());
    }

    @GetMapping("/market/search")
    public ResponseEntity<?> searchMarketStocks(@RequestParam String q) {
        return ResponseEntity.ok(marketDataAndAIService.searchMarketStocks(q));
    }
}