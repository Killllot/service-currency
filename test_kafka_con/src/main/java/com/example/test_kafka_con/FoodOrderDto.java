package com.example.test_kafka_con;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FoodOrderDto {
    String item;
    Double amount;
}
