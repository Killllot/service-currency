package org.example.currencyrateclient.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyrateclient.controller.kafka.KafkaTest;
import org.example.currencyrateclient.model.CurrencyRate;
import org.example.currencyrateclient.services.producer.ProducerCurrency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service("kafkaBooking")
public class KafkaServiceImp implements KafkaService {
    private final ProducerCurrency producer;

    @Autowired
    public KafkaServiceImp(ProducerCurrency producer) {
        this.producer = producer;
    }

    @Override
    public String sendMessage(CurrencyRate currency) throws JsonProcessingException {
        return producer.sendMessage(currency);
    }
}
