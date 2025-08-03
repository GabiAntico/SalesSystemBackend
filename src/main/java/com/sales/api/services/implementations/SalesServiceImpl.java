package com.sales.api.services.implementations;

import com.sales.api.dtos.SaleDto;
import com.sales.api.dtos.SaleRequest;
import com.sales.api.entities.Client;
import com.sales.api.entities.Product;
import com.sales.api.entities.Sale;
import com.sales.api.entities.Seller;
import com.sales.api.models.ClientModel;
import com.sales.api.models.SaleModel;
import com.sales.api.models.SellerModel;
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
            saleModel.setProduct(sale.getProduct());


            saleModel.setClient(new ClientModel(sale.getClient().getId(), sale.getClient().getName()));
            saleModel.setSeller(new SellerModel(sale.getSeller().getId(), sale.getSeller().getName()));

            saleModelList.add(saleModel);
        }
        return saleModelList;
    }

    private List<SaleDto> mapListModelIntoDto(List<SaleModel> saleModelList){

        List<SaleDto> saleDtoList = new ArrayList<>();

        for(SaleModel saleModel : saleModelList){
            SaleDto saleDto = new SaleDto();

            saleDto.setProduct(saleModel.getProduct());
            saleDto.setClient(saleModel.getClient().getName());
            saleDto.setSeller(saleModel.getSeller().getName());

            saleDtoList.add(saleDto);
        }
        return saleDtoList;
    }

    /** Single ojects mapping */

    private SaleModel mapDtoIntoModel(SaleDto saleDto){
        SaleModel saleModel = new SaleModel();

        saleModel.setProduct(saleDto.getProduct());
        saleModel.setClient(new ClientModel(null, saleDto.getClient()));
        saleModel.setSeller(new SellerModel(null, saleDto.getSeller()));

        return saleModel;
    }

    private Sale mapModelIntoEntity(SaleModel saleModel){
        Sale sale = new Sale();

        sale.setProduct(saleModel.getProduct());
        sale.setClient(new Client(saleModel.getClient().getId(), saleModel.getClient().getName()));
        sale.setSeller(new Seller(saleModel.getSeller().getId(), saleModel.getSeller().getName()));

        return sale;
    }

    private SaleModel mapEntityIntoModel(Sale sale){
        SaleModel saleModel = new SaleModel();

        saleModel.setId(saleModel.getId());
        saleModel.setProduct(sale.getProduct());
        saleModel.setClient(new ClientModel(sale.getClient().getId(), sale.getClient().getName()));
        saleModel.setSeller(new SellerModel(sale.getSeller().getId(), sale.getSeller().getName()));

        return saleModel;
    }

    private SaleDto mapModelIntoDto(SaleModel saleModel){
        SaleDto saleDto = new SaleDto();

        saleDto.setProduct(saleModel.getProduct());
        saleDto.setClient(saleModel.getClient().getName());
        saleDto.setSeller(saleModel.getSeller().getName());

        return saleDto;
    }
}
