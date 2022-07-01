package org.example.currencyrateclient.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "tkf-rate-client")
public class TkfRateClientConfig {
    String url;
}
