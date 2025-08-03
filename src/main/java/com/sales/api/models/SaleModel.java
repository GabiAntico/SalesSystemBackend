package com.sales.api.models;

import com.sales.api.entities.Client;
import com.sales.api.entities.SaleDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleModel {

    private Long id;

    private ClientModel client;

    private SellerModel seller;

    private List<SaleDetailModel> details;

    private BigDecimal total;
}
