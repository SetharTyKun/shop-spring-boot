package edu.itc.shop.order;

import java.math.BigDecimal;

public record OrderLine(long productId, int qty, BigDecimal unitPrice) {}
