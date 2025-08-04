package com.sales.api.services.implementations;

import com.sales.api.entities.Product;
import com.sales.api.models.ProductModel;
import com.sales.api.repositories.ProductRepository;
import com.sales.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<ProductModel> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductModel> productModels = new ArrayList<>();
        for(Product product : products){
            productModels.add(mapEntityIntoModel(product));
        }

        return productModels;
    }

    private ProductModel mapEntityIntoModel(Product product){
        return new ProductModel(product.getId(), product.getDescription(), product.getUnitaryPrice());
    }
}
