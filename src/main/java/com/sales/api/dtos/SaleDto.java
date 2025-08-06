package com.sales.api.dtos;

import com.sales.api.models.SaleDetailModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaleDto {
    private Long id;
    private String client;
    private String seller;
    private BigDecimal total;
}
