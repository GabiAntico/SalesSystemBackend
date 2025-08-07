package com.sales.api.services.implementations;

import com.sales.api.entities.Product;
import com.sales.api.models.ProductModel;
import com.sales.api.repositories.ProductRepository;
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
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;


    @Test
    void getAllProducts_returns_ProductModel_list() {
        when(productRepository.findAll()).thenReturn(List.of(new Product(1L, "product1", new BigDecimal(100)), new Product(2L, "product2", new BigDecimal(1000))));

        List<ProductModel> products = productService.getAllProducts();

        assertEquals(ProductModel.class, products.get(0).getClass());
    }

    @Test
    void getAllProducts_returns_empty_list_if_there_are_no_products() {
        when(productRepository.findAll()).thenReturn(List.of());

        List<ProductModel> products = productService.getAllProducts();

        assertEquals(0, products.size());
    }
}