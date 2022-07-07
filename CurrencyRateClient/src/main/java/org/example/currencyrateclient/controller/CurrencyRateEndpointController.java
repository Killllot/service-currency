package org.example.currencyrateclient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyrateclient.model.CurrencyRate;
import org.example.currencyrateclient.model.RateType;
import org.example.currencyrateclient.services.CurrencyRateEndpointService;
import org.example.currencyrateclient.services.KafkaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "${app.rest.api.prefix}")
public class CurrencyRateEndpointController {

    public final CurrencyRateEndpointService currencyRateEndpointService;
    private final KafkaService kafkaService;

    @GetMapping("/currencyRate/{type}/{currency}/{date}")
    public CurrencyRate getCurrencyRate(@PathVariable("type") RateType type,
                                        @PathVariable("currency") String currency,
                                        @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date)  {
        log.info("getCurrencyRate, currency:{}, date:{}", currency, date);

        var rate = currencyRateEndpointService.getCurrencyRate(type, currency, date);
        log.info("rate:{}", rate);
        return rate;
    }

    @PostMapping("/currencyRate/{type}/{currency}/{date}")
    public String getCurrencyRateAndSendInKafka(@PathVariable("type") RateType type,
                                        @PathVariable("currency") String currency,
                                        @DateTimeFormat(pattern = "dd-MM-yyyy") @PathVariable("date") LocalDate date) throws JsonProcessingException {
        log.info("getCurrencyRate, currency:{}, date:{}", currency, date);

        var rate = currencyRateEndpointService.getCurrencyRate(type, currency, date);
        log.info("rate:{}", rate);
        return kafkaService.sendMessage(rate);
    }
}