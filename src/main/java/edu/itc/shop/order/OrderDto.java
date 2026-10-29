package edu.itc.shop.order;

import java.math.BigDecimal;
import java.util.List;

public record OrderDto(long id, OrderStatus status, BigDecimal total, List<OrderLine> lines) {}