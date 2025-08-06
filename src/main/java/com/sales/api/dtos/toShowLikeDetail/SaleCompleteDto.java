package com.sales.api.dtos.toShowLikeDetail;

import com.sales.api.models.ClientModel;
import com.sales.api.models.SellerModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleCompleteDto {
    private Long id;
    private ClientModel client;
    private SellerModel seller;
    private List<SaleDetailDto> details;
    private BigDecimal total;
}
