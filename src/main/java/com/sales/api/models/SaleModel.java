package com.sales.api.models;

import com.sales.api.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleModel {

    private Long id;

    private String product;

    private ClientModel client;

    private SellerModel seller;
}
