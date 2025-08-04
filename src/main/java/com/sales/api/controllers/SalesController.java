package com.sales.api.controllers;

import com.sales.api.dtos.SaleDto;
import com.sales.api.dtos.SaleRequest;
import com.sales.api.services.SalesService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class SalesController {

    @Autowired
    private SalesService salesService;

//    @GetMapping("/sales")
//    public ResponseEntity<List<SaleDto>> getAllSales(){
//        List<SaleDto> sales = salesService.getAllSales();
//
//        return ResponseEntity.ok(sales);
//    }

//    @GetMapping("/products")
//    public ResponseEntity<List<String>> getAllProducts(){
////        List<String> products = new ArrayList<>();
////        products.add("AMD RYZEN 5 4600G");
////        products.add("AMD RYZEN 7 4700");
////        products.add("AMD RYZEN 5 5600G");
////        products.add("AMD RYZEN 5 5600X");
////        products.add("AMD RYZEN 5 5600XT");
////        products.add("AMD RYZEN 7 5800X3D");
////        products.add("AMD RYZEN 9 5950X");
//
//        return ResponseEntity.ok(salesService.getProducts());
//    }

    @PostMapping("/sales")
    public ResponseEntity<SaleDto> postSale(@RequestBody SaleRequest sale){
        SaleDto savedSale = salesService.postSale(sale);

        return ResponseEntity.ok(savedSale);
    }

    @GetMapping("/sales")
    public ResponseEntity<List<SaleDto>> getSalesByClient(@RequestParam(required = false) String name){

        if(name == null || name.isEmpty() || name.isBlank()){
            return ResponseEntity.ok(salesService.getAllSales());
        }

        List<SaleDto> sales = salesService.getSalesByClient(name);

        return ResponseEntity.ok(sales);
    }
}
