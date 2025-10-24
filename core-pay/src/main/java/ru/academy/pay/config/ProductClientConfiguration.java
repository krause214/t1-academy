package ru.academy.pay.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "integration.client.products")
@Getter
@Setter
public class ProductClientConfiguration {
    private String uri;
    private Duration connectTimeout;
    private Duration readTimeout;
    private String getAllPath;
    private String executePaymentPath;
}

