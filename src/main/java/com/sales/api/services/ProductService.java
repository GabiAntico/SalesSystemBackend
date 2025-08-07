package com.sales.api.services;

import com.sales.api.dtos.ProductDto;
import com.sales.api.entities.Product;
import com.sales.api.models.ProductModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<ProductModel> getAllProducts();

    Product getProductByIdToSales(Long productId);

    ProductModel getProductById(Long productId);

    ProductModel postProduct(ProductDto productDto);
}
