package org.example.currencyrateclient.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.currencyrateclient.controller.kafka.KafkaTest;
import org.example.currencyrateclient.model.CurrencyRate;

public interface KafkaService {
    String sendMessage(CurrencyRate currency) throws JsonProcessingException;
}
