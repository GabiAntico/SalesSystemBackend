package com.sales.api.models;

import com.sales.api.entities.Product;
import com.sales.api.entities.Sale;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetailModel {
    private Long id;
    private ProductModel product;
    private int cuantity;
    private BigDecimal price;
    private BigDecimal subtotal;
    private SaleModel sale;
}
