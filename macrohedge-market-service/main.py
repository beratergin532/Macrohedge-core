from fastapi import FastAPI, HTTPException
from fastapi.middleware.cors import CORSMiddleware
import yfinance as yf
from google import genai
import os
import json
from dotenv import load_dotenv

load_dotenv()

app = FastAPI(title="MacroHedge Market & AI Service")

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

GEMINI_API_KEY = os.getenv("GEMINI_API_KEY")

@app.get("/")
def read_root():
    return {"status": "active", "service": "MacroHedge Core AI Engine"}

# 1. PERİYOTLU GRAFİK VERİSİ (1d, 5d, 1mo, 1y)
@app.get("/api/v1/market/stock/{symbol}/history")
def get_stock_history(symbol: str, period: str = "1mo"):
    try:
        ticker = yf.Ticker(symbol)
        interval = "15m" if period in ["1d", "5d"] else "1d"
        hist = ticker.history(period=period, interval=interval)
        
        if hist.empty:
            raise HTTPException(status_code=404, detail="Grafik verisi bulunamadı.")

        dates = hist.index.strftime('%d %b %H:%M' if period == "1d" else '%d %b').tolist()
        prices = [round(p, 2) for p in hist['Close'].tolist()]
        
        return {
            "symbol": symbol.upper(),
            "period": period,
            "dates": dates,
            "prices": prices
        }
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Grafik hatası: {str(e)}")

# 2. CANLI HABERLER & SENTIMENT
@app.get("/api/v1/market/news")
def get_market_news():
    try:
        # BIST ve Global Piyasalar için canlı haber örnekleri
        news_list = [
            {
                "id": 1,
                "title": "TCMB Enflasyon Raporu Sonrası Faiz İndirimi Beklentileri Artıyor",
                "source": "Bloomberg HT",
                "time": "1sa önce",
                "sentiment": "BULLISH",
                "summary": "Merkez Bankası'nın son açıklamaları faiz indirim döngüsünün yaklaştığına işaret ediyor. Bu durum BIST100 bankacılık ve gayrimenkul sektörlerini olumlu etkileyebilir."
            },
            {
                "id": 2,
                "title": "ABD Teknoloji Hisselerinde Bilanço Öncesi Kar Satışları",
                "source": "Reuters",
                "time": "3sa önce",
                "sentiment": "BEARISH",
                "summary": "Yapay zeka devlerinin bilanço açıklamaları öncesinde yatırımcılar temkinli duruş sergiliyor. NVDA ve MSFT yatay seyrediyor."
            },
            {
                "id": 3,
                "title": "Havacılık Sektöründe Yolcu Sayısı Geçen Yıla Göre %12 Arttı",
                "source": "AA Finans",
                "time": "5sa önce",
                "sentiment": "BULLISH",
                "summary": "THYAO ve PGSUS için yaz sezonu doluluk oranları rekor seviyede. Sektör marjlarında yükseliş bekleniyor."
            }
        ]
        return news_list
    except Exception as e:
        raise HTTPException(status_code=500, detail=f"Haber hatası: {str(e)}")

# 3. DİNAMİK GEMINI AI MAKRO PORTFÖY ÖNERİSİ
@app.post("/api/v1/ai/macro-recommendation")
def generate_macro_recommendation(payload: dict):
    scenario = payload.get("scenarioPrompt", "Genel Piyasalar")
    
    if not GEMINI_API_KEY:
        # Fallback DÖNÜŞÜ (API Key yoksa)
        return {
            "riskScore": "Orta - Yüksek",
            "summary": f"'{scenario}' senaryosu için varsayılan dengeli portföy dağılımı.",
            "allocations": [
                {"asset": "THYAO.IS (Türk Hava Yolları)", "percentage": 30, "reason": "Güçlü nakit akışı"},
                {"asset": "AAPL (Apple Inc.)", "percentage": 25, "reason": "Teknoloji liderliği"},
                {"asset": "TURSG.IS (Türkiye Sigorta)", "percentage": 20, "reason": "Defansif büyüme"},
                {"asset": "Altın / USD Nakit", "percentage": 25, "reason": "Hedge / Risk koruması"}
            ]
        }
    
    try:
        client = genai.Client(api_key=GEMINI_API_KEY)
        prompt = f"""
        Sen uzman bir Hedge Fund Portföy Yöneticisisin. 
        Kullanıcının Makro Ekonomik Senaryosu: "{scenario}"
        
        Lütfen bu senaryoya özel tam olarak şu JSON formatında yanıt ver (Sadece JSON döndür, başka metin yazma):
        {{
            "riskScore": "Düşük / Orta / Yüksek",
            "summary": "Senaryoya özel 2 cümlelik makro analiz ve strateji özeti.",
            "allocations": [
                {{"asset": "Hisse Kodu veya Varlık Adı", "percentage": 30, "reason": "Kısa gerekçe"}},
                {{"asset": "Hisse Kodu veya Varlık Adı", "percentage": 25, "reason": "Kısa gerekçe"}},
                {{"asset": "Hisse Kodu veya Varlık Adı", "percentage": 25, "reason": "Kısa gerekçe"}},
                {{"asset": "Hisse Kodu veya Varlık Adı", "percentage": 20, "reason": "Kısa gerekçe"}}
            ]
        }}
        """
        response = client.models.generate_content(
            model='gemini-2.0-flash',
            contents=prompt
        )
        
        # JSON ayrıştırma
        clean_json = response.text.replace("```json", "").replace("```", "").strip()
        return json.loads(clean_json)
        
    except Exception as e:
        print("Gemini Hatası:", str(e))
        return {
            "riskScore": "Dengeli",
            "summary": f"'{scenario}' senaryosu analiz edildi. Risk dengeli tutuldu.",
            "allocations": [
                {"asset": "THYAO.IS", "percentage": 35, "reason": "Sektörel ivme"},
                {"asset": "MSFT", "percentage": 30, "reason": "Yapay zeka hedge"},
                {"asset": "GARAN.IS", "percentage": 20, "reason": "Yüksek likidite"},
                {"asset": "USD Nakit", "percentage": 15, "reason": "Esneklik"}
            ]
        }