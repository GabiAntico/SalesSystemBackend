package com.sales.api.services.implementations;

import com.sales.api.dtos.ClientRequest;
import com.sales.api.dtos.SellerRequest;
import com.sales.api.entities.Seller;
import com.sales.api.models.SellerModel;
import com.sales.api.repositories.SellerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SellerServiceImplTest {

    @Autowired
    private SellerServiceImpl sellerService;

    @MockitoBean
    private SellerRepository sellerRepository;

    @Test
    void getAllSellers_returns_clientModel_list() {
        when(sellerRepository.findAll()).thenReturn(List.of(new Seller(1L, "seller1"), new Seller(2L, "seller2"), new Seller(3L, "seller3")));

        List<SellerModel> sellers = sellerService.getAllSellers();

        assertEquals(SellerModel.class, sellers.get(0).getClass());
    }

    @Test
    void getAllSellers_returns_empty_list_if_there_are_no_sellers() {
        when(sellerRepository.findAll()).thenReturn(List.of());

        List<SellerModel> sellers = sellerService.getAllSellers();

        assertEquals(0, sellers.size());
    }

    @Test
    void createSeller_throws_exception_if_seller_already_exists() {
        when(sellerRepository.findByName("seller1")).thenReturn(new Seller(1L, "seller1"));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> sellerService.createSeller(new SellerRequest("seller1")));

        assertEquals("This name already exists", exception.getReason());
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    void createSeller_returns_created_seller_as_SellerModel() {
        Seller sellerToSave = new Seller(null, "seller1");
        Seller sellerSaved = new Seller(1L, "seller1");

        when(sellerRepository.findByName("seller1")).thenReturn(null);
        when(sellerRepository.save(any(Seller.class))).thenReturn(sellerSaved);

        SellerModel seller = sellerService.createSeller(new SellerRequest("seller1"));

        assertEquals(1L, seller.getId());
        assertEquals("seller1", seller.getName());
        assertEquals(SellerModel.class, seller.getClass());
    }
}