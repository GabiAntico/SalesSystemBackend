package com.sales.api.services.implementations;

import com.sales.api.dtos.SellerRequest;
import com.sales.api.entities.Client;
import com.sales.api.entities.Seller;
import com.sales.api.models.SellerModel;
import com.sales.api.repositories.SellerRepository;
import com.sales.api.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public List<SellerModel> getAllSellers() {
        List<Seller> sellers = sellerRepository.findAll();

        List<SellerModel> sellersModels = new ArrayList<>();
        for(Seller seller : sellers){
            sellersModels.add(mapEntityIntoModel(seller));
        }
        return sellersModels;
    }

    @Override
    public SellerModel createSeller(SellerRequest sellerRequest) {
        Seller sellerWithName = sellerRepository.findByName(sellerRequest.getName());

        if(sellerWithName != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This name already exists");
        }

        Seller sellerEntity = new Seller();

        sellerEntity.setName(sellerRequest.getName());
        return mapEntityIntoModel(sellerRepository.save(sellerEntity));
    }

    @Override
    public Seller getSellerById(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found"));

        return seller;
    }

    private SellerModel mapEntityIntoModel(Seller seller){
        return new SellerModel(seller.getId(), seller.getName());
    }
}
