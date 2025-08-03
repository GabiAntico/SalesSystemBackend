package com.sales.api.dtos;

import java.math.BigDecimal;

public class SaleDetailRequest {
    private Long productId;
    private BigDecimal price;
    private int cuantity;
    private BigDecimal subtotal;
}
