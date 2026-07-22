package com.tradecore.macrohedge_backend.service;

import com.tradecore.macrohedge_backend.dto.MacroAIRequest;
import com.tradecore.macrohedge_backend.dto.MacroAIResponse;
import com.tradecore.macrohedge_backend.dto.StockPriceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MarketDataAndAIService {

    private final RestClient restClient;

    @Value("${python.market-service.url}")
    private String pythonServiceUrl;

    public MarketDataAndAIService(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Python Mikroservisinden Canlı Borsa Fiyatı Çeker
     */
    public StockPriceResponse getStockPrice(String symbol) {
        return restClient.get()
                .uri(pythonServiceUrl + "/api/v1/market/stock/" + symbol)
                .retrieve()
                .body(StockPriceResponse.class);
    }

    /**
     * Python Mikroservisinden Yapay Zeka Makro Önerisi Alır
     */
    public MacroAIResponse getMacroRecommendation(MacroAIRequest request) {
        return restClient.post()
                .uri(pythonServiceUrl + "/api/v1/ai/macro-recommendation")
                .body(request)
                .retrieve()
                .body(MacroAIResponse.class);
    }
}