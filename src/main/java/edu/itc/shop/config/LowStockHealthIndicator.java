package edu.itc.shop.config;

import java.util.List;

import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component("lowStock")
public class LowStockHealthIndicator implements HealthIndicator {
    private final JdbcClient jdbc;
    private final ShopProperties props;

    public LowStockHealthIndicator(JdbcClient jdbc, ShopProperties props) {
        this.jdbc = jdbc;
        this.props = props;
    }

    @Override
    public Health health() {
        List<String> low = jdbc.sql("SELECT name FROM product WHERE stock < :t ORDER BY name")
                .param("t", props.lowStockThreshold())
                .query(String.class)
                .list();
        if (low.isEmpty()) {
            return Health.up().build();
        }
        return Health.down().withDetail("lowStockProducts", low).build();
    }
}