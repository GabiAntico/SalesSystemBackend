package com.sales.api.dtos.toShowLikeDetail;

import com.sales.api.models.ProductModel;
import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleDetailDto {
    private Long id;
    private ProductModel product;
    private int cuantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
