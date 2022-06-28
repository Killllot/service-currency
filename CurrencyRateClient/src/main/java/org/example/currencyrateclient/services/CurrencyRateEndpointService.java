package org.example.currencyrateclient.services;

import lombok.extern.slf4j.Slf4j;

import org.example.currencyrateclient.controller.clients.RateClient;
import org.example.currencyrateclient.model.CurrencyRate;
import org.example.currencyrateclient.model.RateType;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.util.Map;

@Service
@Slf4j
public class CurrencyRateEndpointService {

    private final Map<String, RateClient> clients;

    public CurrencyRateEndpointService(Map<String, RateClient> clients) {
        this.clients = clients;
    }

    public CurrencyRate getCurrencyRate(RateType rateType, String currency, LocalDate date) {
        log.info("getCurrencyRate. rateType:{}, currency:{}, date:{}", rateType, currency, date);
        var client = clients.get(rateType.getServiceName());
        return client.getCurrencyRate(currency, date);
    }
}
