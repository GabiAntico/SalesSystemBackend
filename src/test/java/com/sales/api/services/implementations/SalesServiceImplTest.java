package com.sales.api.services.implementations;

import com.sales.api.dtos.SaleDto;
import com.sales.api.entities.*;
import com.sales.api.repositories.SalesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SalesServiceImplTest {

    @InjectMocks
    private SalesServiceImpl salesService;

    @Mock
    private SalesRepository salesRepository;

    @Test
    void getAllSales_return_SaleDto_list() {
        when(salesRepository.findAll()).thenReturn(List.of(
                new Sale(1L,
                        new Client(1L, "client1"),
                        new Seller(1L, "seller1"),
                        List.of(
                        new SaleDetail(1L,
                                new Product(1L, "product1", new BigDecimal(100)),
                                10,
                                new BigDecimal(100),
                                new BigDecimal(1000),
                                new Sale(1L, null, null, null, null))
                ),
                        new BigDecimal(1000))
        ));

        List<SaleDto> sales = salesService.getAllSales();

        assertEquals(SaleDto.class, sales.get(0).getClass());
    }

    @Test
    void postSale() {
    }

    @Test
    void getSalesByClient() {
    }
}