package com.egnyte.exchangerates;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/exchange/rates")
public class ExchangeRatesController {

    @GetMapping
    public Map<String, Float> rates() {
        Random rand = new Random();
        float usd = roundPrecision2(4 + rand.nextFloat(0.5F));
        float euro = roundPrecision2(4.2F + rand.nextFloat(0.5F));
        float chf = roundPrecision2(4.6F + rand.nextFloat(0.5F));

        return Map.of("USD", usd, "EUR", euro, "CHF", chf);
    }

    private static float roundPrecision2(float number) {
        return Math.round(number * 100.0f) / 100.0f;
    }
}
