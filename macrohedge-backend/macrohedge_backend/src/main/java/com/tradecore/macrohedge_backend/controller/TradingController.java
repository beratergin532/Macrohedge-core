package com.tradecore.macrohedge_backend.controller;

import com.tradecore.macrohedge_backend.dto.TradeRequest;
import com.tradecore.macrohedge_backend.service.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/trade")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class TradingController {

    private final TradingService tradingService;

    @PostMapping("/buy")
    public ResponseEntity<?> buyStock(@RequestBody TradeRequest request) {
        tradingService.buyStock(request);
        return ResponseEntity.ok(Map.of("success", true, "message", "Hisse alım işlemi başarıyla gerçekleşti."));
    }

    @PostMapping("/sell")
    public ResponseEntity<?> sellStock(@RequestBody TradeRequest request) {
        tradingService.sellStock(request);
        return ResponseEntity.ok(Map.of("success", true, "message", "Hisse satım işlemi başarıyla gerçekleşti."));
    }
}