package edu.itc.shop.config;

import java.math.BigDecimal;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Validated
@ConfigurationProperties("shop")
public record ShopProperties(
        @NotBlank String currency,
        @NotNull @DecimalMin("0.0") BigDecimal taxRate,
        @Min(0) int lowStockThreshold) {
}