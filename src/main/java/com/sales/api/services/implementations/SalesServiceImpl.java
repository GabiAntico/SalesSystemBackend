package com.sales.api.services.implementations;

import com.sales.api.dtos.SaleDetailRequest;
import com.sales.api.dtos.SaleDto;
import com.sales.api.dtos.SaleRequest;
import com.sales.api.entities.*;
import com.sales.api.models.*;
import com.sales.api.repositories.SalesRepository;
import com.sales.api.services.ClientService;
import com.sales.api.services.ProductService;
import com.sales.api.services.SalesService;
import com.sales.api.services.SellerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    /**
     * Auxiliary services
     */

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private SellerService sellerService;

    @Override
    public List<SaleDto> getAllSales() {
        List<Sale> salesEntities = salesRepository.findAll();

        return mapListModelIntoDto(mapListEntityIntoModel(salesEntities));
    }

    @Override
    public SaleDto postSale(SaleRequest sale) {

        List<SaleDetail> details = new ArrayList<>();

        BigDecimal total = new BigDecimal(0);

        Client client = clientService.getClientById(sale.getClientId());

        Seller seller = sellerService.getSellerById(sale.getSellerId());

        Sale saleToSave = new Sale(null, client, seller, null, null);

        for(SaleDetailRequest saleDetailRequest : sale.getDetails()){


            Product product = productService.getProductById(saleDetailRequest.getProductId());

            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setProduct(product);
            saleDetail.setCuantity(saleDetailRequest.getCuantity());
            saleDetail.setPrice(product.getUnitaryPrice());

            BigDecimal subtotal = product.getUnitaryPrice();
            subtotal = subtotal.multiply(new BigDecimal(saleDetail.getCuantity()));

            saleDetail.setSubtotal(subtotal);

            total = total.add(subtotal);

            saleDetail.setSale(saleToSave);

            details.add(saleDetail);
        }

        saleToSave.setDetails(details);
        saleToSave.setTotal(total);

        Sale saleSaved = salesRepository.save(saleToSave);

        return mapModelIntoDto(mapEntityIntoModel(saleSaved));
    }

    @Override
    public List<SaleDto> getSalesByClient(String name) {
        List<Sale> sales = salesRepository.findAllByClientIgnoreCase(name);

        return mapListModelIntoDto(mapListEntityIntoModel(sales));
    }

    @Override
    public SaleModel getSaleById(Long saleId) {
        Sale sale = salesRepository.findById(saleId).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));

        return mapEntityIntoModel(sale);
    }


    /**
     * Lists Mapping
     */

    private List<SaleModel> mapListEntityIntoModel(List<Sale> salesEntities){

        List<SaleModel> saleModelList = new ArrayList<>();

        for(Sale sale : salesEntities){
            SaleModel saleModel = new SaleModel();

            saleModel.setId(sale.getId());
            saleModel.setClient(new ClientModel(sale.getClient().getId(), sale.getClient().getName()));
            saleModel.setSeller(new SellerModel(sale.getSeller().getId(), sale.getSeller().getName()));
            saleModel.setTotal(sale.getTotal());

            List<SaleDetailModel> saleDetailModelLst = new ArrayList<>();
            for(SaleDetail saleDetail : sale.getDetails()){
                SaleDetailModel saleDetailModel = new SaleDetailModel(
                        saleDetail.getId(),
                        new ProductModel(saleDetail.getProduct().getId(), saleDetail.getProduct().getDescription(),
                                saleDetail.getProduct().getUnitaryPrice()),
                        saleDetail.getCuantity(),
                        saleDetail.getPrice(),
                        saleDetail.getSubtotal(),
                        new SaleModel(saleDetail.getSale().getId(), null, null, null, null)
                );

                saleDetailModelLst.add(saleDetailModel);
            }
            saleModel.setDetails(saleDetailModelLst);
            saleModelList.add(saleModel);
        }

        return saleModelList;
    }

    private List<SaleDto> mapListModelIntoDto(List<SaleModel> saleModelList){

        List<SaleDto> saleDtoList = new ArrayList<>();

        for(SaleModel saleModel : saleModelList){
            SaleDto saleDto = new SaleDto();

            saleDto.setClient(saleModel.getClient().getName());
            saleDto.setSeller(saleModel.getSeller().getName());
            saleDto.setTotal(saleModel.getTotal());

            saleDtoList.add(saleDto);
        }
        return saleDtoList;
    }

    /** Single ojects mapping */


    /* This method is obsolete because it never needs map SaleDto to SaleModel */

//    private SaleModel mapDtoIntoModel(SaleDto saleDto){
//        SaleModel saleModel = new SaleModel();
//
//        saleModel.setProduct(saleDto.getProduct());
//        saleModel.setClient(new ClientModel(null, saleDto.getClient()));
//        saleModel.setSeller(new SellerModel(null, saleDto.getSeller()));
//
//        return saleModel;
//    }


    private Sale mapModelIntoEntity(SaleModel saleModel){
        Sale sale = new Sale();

        sale.setId(saleModel.getId());
        sale.setClient(new Client(saleModel.getClient().getId(), saleModel.getClient().getName()));
        sale.setSeller(new Seller(saleModel.getSeller().getId(), saleModel.getSeller().getName()));
        sale.setTotal(saleModel.getTotal());

        List<SaleDetail> saleDetailLst = new ArrayList<>();
        for(SaleDetailModel saleDetailModel : saleModel.getDetails()){
            SaleDetail saleDetail = new SaleDetail();

            saleDetail.setId(saleDetailModel.getId());
            saleDetail.setCuantity(saleDetailModel.getCuantity());
            saleDetail.setPrice(saleDetailModel.getPrice());
            saleDetail.setSubtotal(saleDetailModel.getSubtotal());
            saleDetail.setProduct(new Product(saleDetailModel.getProduct().getId(), saleDetailModel.getProduct().getDescription(),
                    saleDetailModel.getProduct().getUnitaryPrice()));
            saleDetail.setSale(new Sale(saleDetailModel.getSale().getId(), null, null, null, null));

            saleDetailLst.add(saleDetail);
        }
        sale.setDetails(saleDetailLst);

        return sale;
    }

    private SaleModel mapEntityIntoModel(Sale sale){
        SaleModel saleModel = new SaleModel();

        saleModel.setId(sale.getId());
        saleModel.setClient(new ClientModel(sale.getClient().getId(), sale.getClient().getName()));
        saleModel.setSeller(new SellerModel(sale.getSeller().getId(), sale.getSeller().getName()));
        saleModel.setTotal(sale.getTotal());

        List<SaleDetailModel> saleDetailModelLst = new ArrayList<>();

        for(SaleDetail saleDetail : sale.getDetails()){
            SaleDetailModel saleDetailModel = new SaleDetailModel();

            saleDetailModel.setId(saleDetail.getId());
            saleDetailModel.setCuantity(saleDetailModel.getCuantity());
            saleDetailModel.setPrice(saleDetail.getPrice());
            saleDetailModel.setSubtotal(saleDetail.getSubtotal());
            saleDetailModel.setProduct(new ProductModel(saleDetail.getProduct().getId(), saleDetail.getProduct().getDescription(),
                                                        saleDetail.getProduct().getUnitaryPrice()));
            saleDetailModel.setSale(new SaleModel(saleDetail.getSale().getId(), null, null, null, null));

            saleDetailModelLst.add(saleDetailModel);
        }
        saleModel.setDetails(saleDetailModelLst);

        return saleModel;
    }

    private SaleDto mapModelIntoDto(SaleModel saleModel){
        SaleDto saleDto = new SaleDto();

        saleDto.setClient(saleModel.getClient().getName());
        saleDto.setSeller(saleModel.getSeller().getName());
        saleDto.setTotal(saleModel.getTotal());

        return saleDto;
    }
}
