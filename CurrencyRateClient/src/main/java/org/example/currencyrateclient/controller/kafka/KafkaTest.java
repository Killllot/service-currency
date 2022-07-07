package org.example.currencyrateclient.controller.kafka;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class KafkaTest {
    String item;
    Double amount;
}
