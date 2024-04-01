package com.example.demo3.services;

import com.example.demo3.model.ExchangeRateDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExchangeRateService {
    private static final String API_URL = "https://api.fxratesapi.com/latest";

    public Map<String, Double> getRates() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Double> rates = new HashMap<>();
        if (response.statusCode() == 200) {
            String body = response.body();
            JSONObject json = new JSONObject(body);
            JSONArray ratesArray = json.getJSONArray("rates");
            for (int i = 0; i < ratesArray.length(); i++) {
                JSONObject rate = ratesArray.getJSONObject(i);
                String currency = rate.getString("currency");
                Double rateValue = rate.getDouble("rate");
                rates.put(currency, rateValue);
            }
        }
        return rates;
    }



}
