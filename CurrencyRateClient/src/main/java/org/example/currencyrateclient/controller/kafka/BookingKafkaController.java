package org.example.currencyrateclient.controller.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.example.currencyrateclient.model.CurrencyRate;
import org.example.currencyrateclient.services.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/kafka")
public class BookingKafkaController {
    private final KafkaService kafkaService;

    @Autowired
    public BookingKafkaController(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @PostMapping
    public String createBooking(@RequestBody CurrencyRate currency) throws JsonProcessingException {
        log.info("send currency request received");
        return kafkaService.sendMessage(currency);
    }
}
