package com.tradecore.macrohedge_backend.controller;

import com.tradecore.macrohedge_backend.entity.Portfolio;
import com.tradecore.macrohedge_backend.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/portfolio")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioRepository portfolioRepository;

    @GetMapping
    public ResponseEntity<List<Portfolio>> getPortfolio(@RequestParam(defaultValue = "1") Long userId) {
        return ResponseEntity.ok(portfolioRepository.findByUserId(userId));
    }
}