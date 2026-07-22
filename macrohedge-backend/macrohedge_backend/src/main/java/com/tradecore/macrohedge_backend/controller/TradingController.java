package com.tradecore.macrohedge_backend.controller;

import com.tradecore.macrohedge_backend.dto.TradeRequest;
import com.tradecore.macrohedge_backend.service.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/trade")
@RequiredArgsConstructor
public class TradingController {

    private final TradingService tradingService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyStock(@RequestBody TradeRequest request) {
        tradingService.buyStock(request);
        return ResponseEntity.ok("Hisse alım işlemi başarıyla gerçekleşti.");
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@RequestBody TradeRequest request) {
        tradingService.sellStock(request);
        return ResponseEntity.ok("Hisse satım işlemi başarıyla gerçekleşti.");
    }
}