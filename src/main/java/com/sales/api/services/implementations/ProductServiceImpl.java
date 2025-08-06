package com.sales.api.services.implementations;

import com.sales.api.entities.Product;
import com.sales.api.models.ProductModel;
import com.sales.api.repositories.ProductRepository;
import com.sales.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    @Override
    public Product getProductByIdToSales(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "The inserted product does not exists."));

        return product;
    }

    @Override
    public ProductModel getProductById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "The product was not found."));

        return mapEntityIntoModel(product);
    }

    private ProductModel mapEntityIntoModel(Product product){
        return new ProductModel(product.getId(), product.getDescription(), product.getUnitaryPrice());
    }
}
