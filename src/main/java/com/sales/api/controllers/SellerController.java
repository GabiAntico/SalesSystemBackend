package com.sales.api.controllers;

import com.sales.api.dtos.SellerRequest;
import com.sales.api.models.SellerModel;
import com.sales.api.services.SellerService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @GetMapping()
    public ResponseEntity<List<SellerModel>> getAllSellers(){
        return ResponseEntity.ok(sellerService.getAllSellers());
    }

    @PostMapping()
    public ResponseEntity<SellerModel> createNewSeller(@RequestBody SellerRequest sellerRequest){
        return ResponseEntity.ok(sellerService.createSeller(sellerRequest));
    }
}g
