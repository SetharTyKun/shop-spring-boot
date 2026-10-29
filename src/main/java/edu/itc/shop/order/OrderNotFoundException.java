package edu.itc.shop.order;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(long orderId) {
        super("Order " + orderId + " not found");
    }
}