package org.example.currencyrateclient.controller.clients;



import org.example.currencyrateclient.model.CurrencyRate;

import java.time.LocalDate;

public interface RateClient {

    CurrencyRate getCurrencyRate(String currency, LocalDate date);
}
