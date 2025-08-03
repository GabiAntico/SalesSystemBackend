package com.sales.api.dtos;

import com.sales.api.entities.SaleDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequest {
    private Long productId;
    private Long clientId;
    private Long sellerId;
    private List<SaleDetailRequest> details;
}
