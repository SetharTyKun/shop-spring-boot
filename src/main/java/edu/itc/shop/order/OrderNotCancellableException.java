package edu.itc.shop.order;

public class OrderNotCancellableException extends RuntimeException {

    public OrderNotCancellableException(long orderId, OrderStatus status) {
        super("Order " + orderId + " cannot be cancelled (status " + status + ")");
    }
}