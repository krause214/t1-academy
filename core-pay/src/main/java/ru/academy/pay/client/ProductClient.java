package ru.academy.pay.client;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.academy.pay.config.ProductClientConfiguration;
import ru.academy.pay.model.ListProductResponseDto;
import ru.academy.pay.model.ProductDto;
import ru.academy.pay.model.ProductPaymentExecutionDto;

import java.math.BigDecimal;

@Component
@EnableConfigurationProperties(ProductClientConfiguration.class)
public class ProductClient {

    private final RestTemplate client;

    private final ProductClientConfiguration configuration;

    public ProductClient(ProductClientConfiguration configuration) {
        this.configuration = configuration;
        this.client = new RestTemplateBuilder()
                .rootUri(configuration.getUri())
                .connectTimeout(configuration.getConnectTimeout())
                .readTimeout(configuration.getReadTimeout())
                .build();
    }

    public ListProductResponseDto getAllProducts() {
        return client.getForObject(
                configuration.getGetAllPath(),
                ListProductResponseDto.class);
    }


    public ProductDto executePayment(String accountNumber, BigDecimal paymentAmount) {
        return client.postForObject(
                configuration.getExecutePaymentPath(),
                new ProductPaymentExecutionDto(paymentAmount),
                ProductDto.class,
                accountNumber);
    }
}
