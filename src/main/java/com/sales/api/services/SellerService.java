package com.sales.api.services;

import com.sales.api.dtos.SellerRequest;
import com.sales.api.models.SellerModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerService {
    List<SellerModel> getAllSellers();

    SellerModel createSeller(SellerRequest sellerRequest);
}
