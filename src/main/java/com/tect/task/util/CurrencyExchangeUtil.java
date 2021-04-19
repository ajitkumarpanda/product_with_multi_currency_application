package com.tect.task.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

@Component
public class CurrencyExchangeUtil {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    @Value("${fixer.io.url}")
    private String fixerIOUrl;
    @Value("${fixer.io.apiaccesskey}")
    private String fixerIOApiAccessKey;

    public double convert(String to, double amount) {
        if (fixerIOApiAccessKey == null
                || fixerIOApiAccessKey.isEmpty()
                || "${fixer.io.apiaccesskey}".equals(fixerIOApiAccessKey)) {
            throw new IllegalArgumentException("FixerIO Api Access Key is not set");
        }

        final FixerIOResponse fixerIOResponse =
                REST_TEMPLATE.getForEntity(URI.create(fixerIOUrl), FixerIOResponse.class).getBody();

        if (Objects.nonNull(fixerIOResponse) && fixerIOResponse.getRates() == null) {
            throw new IllegalStateException("No rates were retrieved from Fixer IO");
        }

        return amount * (1.0 / fixerIOResponse.getRates().get(to));
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class FixerIOResponse {
        private Map<String, Double> rates;

        public Map<String, Double> getRates() {
            return rates;
        }

        public void setRates(Map<String, Double> rates) {
            this.rates = rates;
        }
    }
}

