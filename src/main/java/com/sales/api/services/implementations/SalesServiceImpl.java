package com.sales.api.services.implementations;

import com.sales.api.dtos.SaleDto;
import com.sales.api.dtos.SaleRequest;
import com.sales.api.entities.*;
import com.sales.api.models.*;
import com.sales.api.repositories.SalesRepository;
import com.sales.api.services.SalesService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesServiceImpl implements SalesService {

    List<String> products = new ArrayList<>();

    public SalesServiceImpl() {
        products.add("AMD RYZEN 5 4600G");
        products.add("AMD RYZEN 7 4700");
        products.add("AMD RYZEN 5 5600G");
        products.add("AMD RYZEN 5 5600X");
        products.add("AMD RYZEN 5 5600XT");
        products.add("AMD RYZEN 7 5800X3D");
        products.add("AMD RYZEN 9 5950X");
    }

    @Override
    public List<String> getProducts(){
        return this.products;
    }

//    public void addProduct(String product){
//        products.add(product);
//    }

    @Autowired
    private SalesRepository salesRepository;

    @Override
    public List<SaleDto> getAllSales() {
        List<Sale> salesEntities = salesRepository.findAll();

        return mapListModelIntoDto(mapListEntityIntoModel(salesEntities));
    }

    @Override
    public SaleDto postSale(SaleRequest sale) {

        Sale saleToSave = new Sale(null, new Client(sale.getClientId(), null), new Seller(sale.getSellerId(), null), null);

        salesRepository.save(saleToSave);

        return mapModelIntoDto(mapEntityIntoModel(saleToSave));
    }

    @Override
    public List<SaleDto> getSalesByClient(String name) {
        List<Sale> sales = salesRepository.findAllByClientIgnoreCase(name);

        return mapListModelIntoDto(mapListEntityIntoModel(sales));
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

        saleModel.setId(saleModel.getId());
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
