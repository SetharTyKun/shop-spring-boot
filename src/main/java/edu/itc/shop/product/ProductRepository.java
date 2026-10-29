package edu.itc.shop.product;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository // tells Spring “create and manage one instance of this class”, and marks it as the data-access layer.
public class ProductRepository {

    // The helper that runs SQL.
    private final JdbcClient jdbc;

    public ProductRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public int updateStock(long productId, int delta) {
        return jdbc.sql("""
                UPDATE product
                SET stock = stock + :delta
                WHERE id = :id AND stock + :delta >= 0
                """)
                .param("delta", delta)
                .param("id", productId)
                .update();
    }

    public Optional<BigDecimal> findPrice(long productId) {
        return jdbc.sql("SELECT price FROM product WHERE id = :id")
                .param("id", productId)
                .query(BigDecimal.class)
                .optional();
    }
}