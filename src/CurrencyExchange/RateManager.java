package CurrencyExchange;

import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class RateManager {
    private final Map<String, Double> ratesCache;
    private JSONObject allRatesJson;

    public RateManager(String baseCurrency) {
        this.ratesCache = new HashMap<>();
        this.allRatesJson = fetchRates(baseCurrency);

        if (this.allRatesJson != null) {
            updateCache("usd");
            updateCache("eur");
        }
    }

    // Перевіряє, чи є валюта в кеші
    public boolean isCached(String currency) {
        return ratesCache.containsKey(currency.toLowerCase());
    }

    // Отримує курс (якщо немає в кеші - додає з JSON)
    public double getRate(String currency) {
        String code = currency.toLowerCase();

        if (!ratesCache.containsKey(code)) {
            updateCache(code);
        }

        // Якщо валюти немає навіть в JSON
        return ratesCache.getOrDefault(code, -1.0);
    }

    // Внутрішній метод: бере дані з JSON і кладе в Map
    private void updateCache(String currency) {
        String code = currency.toLowerCase();
        if (allRatesJson != null && allRatesJson.has(code)) {
            double rate = allRatesJson.getJSONObject(code).getDouble("rate");
            ratesCache.put(code, rate);
        }
    }

    // Приватний метод для запиту до API
    private JSONObject fetchRates(String baseCurrency) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = "http://www.floatrates.com/daily/" + baseCurrency.toLowerCase() + ".json";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return new JSONObject(response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error fetching data: " + e.getMessage());
        }
        return null;
    }
}
