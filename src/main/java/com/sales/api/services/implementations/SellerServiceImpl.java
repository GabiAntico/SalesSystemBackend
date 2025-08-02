package com.sales.api.services.implementations;

import com.sales.api.entities.Seller;
import com.sales.api.models.SellerModel;
import com.sales.api.repositories.SellerRepository;
import com.sales.api.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private SellerModel mapEntityIntoModel(Seller seller){
        return new SellerModel(seller.getId(), seller.getName());
    }
}
