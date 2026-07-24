package com.tradecore.macrohedge_backend.service;

import com.tradecore.macrohedge_backend.dto.MacroAIRequest;
import com.tradecore.macrohedge_backend.dto.MacroAIResponse;
import com.tradecore.macrohedge_backend.dto.StockHistoryItem;
import com.tradecore.macrohedge_backend.dto.StockPriceResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class MarketDataAndAIService {

    private final RestClient restClient;

    @Value("${python.market-service.url}")
    private String pythonServiceUrl;

    public MarketDataAndAIService(RestClient restClient) {
        this.restClient = restClient;
    }

    public StockPriceResponse getStockPrice(String symbol) {
        return restClient.get()
                .uri(pythonServiceUrl + "/api/v1/market/stock/" + symbol)
                .retrieve()
                .body(StockPriceResponse.class);
    }

    public List<StockHistoryItem> getStockHistory(String symbol, String period) {
        return restClient.get()
                .uri(pythonServiceUrl + "/api/v1/market/stock/" + symbol + "/history?period=" + period)
                .retrieve()
                .body(new ParameterizedTypeReference<List<StockHistoryItem>>() {});
    }

    public MacroAIResponse getMacroRecommendation(MacroAIRequest request) {
        return restClient.post()
                .uri(pythonServiceUrl + "/api/v1/ai/macro-recommendation")
                .body(request)
                .retrieve()
                .body(MacroAIResponse.class);
    }

    public Object getMarketNews() {
        return restClient.get()
                .uri(pythonServiceUrl + "/api/v1/market/news")
                .retrieve()
                .body(Object.class);
    }

    public Object searchMarketStocks(String query) {
        return restClient.get()
                .uri(pythonServiceUrl + "/api/v1/market/search?q=" + query)
                .retrieve()
                .body(Object.class);
    }
}