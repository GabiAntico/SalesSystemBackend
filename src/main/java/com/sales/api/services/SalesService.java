package com.sales.api.services;

import com.sales.api.dtos.SaleDto;
import com.sales.api.dtos.SaleRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SalesService {

    List<SaleDto> getAllSales();

    SaleDto postSale(SaleRequest sale);

    List<SaleDto> getSalesByClient(String name);
}
