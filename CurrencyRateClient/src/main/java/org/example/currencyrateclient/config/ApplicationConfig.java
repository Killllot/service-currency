package org.example.currencyrateclient.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({CbrRateClientConfig.class,TkfRateClientConfig.class})
public class ApplicationConfig {

}
