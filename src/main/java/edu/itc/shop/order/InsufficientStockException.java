package edu.itc.shop.order;

public class InsufficientStockException extends RuntimeException {

    private final long productId;

    public InsufficientStockException(long productId) {
        super("Insufficient stock for product " + productId);
        this.productId = productId;
    }

    public long getProductId() {
        return productId;
    }
}