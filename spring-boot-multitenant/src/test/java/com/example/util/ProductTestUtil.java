package com.example.util;

import com.example.controller.manager.ProductManager;
import com.example.controller.service.ProductService;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.mapper.ProductMapper;
import com.example.entity.order.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductTestUtil {
    @Autowired
    ProductManager productManager;

    @Autowired
    ProductService productService;



    public  List<ProductDto> createProducts() throws Exception {
        List<ProductDto> products = new ArrayList<>();
        products.add(saveProductIfNotPresent(getProductDto1("Machine")));
        products.add(saveProductIfNotPresent(getProductDto1("Raptor")));
        products.add(saveProductIfNotPresent(getProductDto1("Lumber")));
        products.add(saveProductIfNotPresent(getProductDto1("Table")));
        return products;
    }

    public void deleteAllProducts() throws Exception  {
        List<ProductDto> productList =  productManager.getAllProducts();
        for(ProductDto dto : productList) {
            deleteProductById(dto.getId());
        }
    }

    public void deleteProductById(Long id) throws Exception {
        Product product = (Product) productService.find(Product.class, id);
        productService.remove(product);
    }

    public ProductDto saveProductIfNotPresent(ProductDto productDto) throws Exception {
        Product dbProduct  = productService.findByProductName(productDto.getName());
        if(dbProduct != null) {
            productDto =  ProductMapper.mapperEntityToDto.apply(dbProduct);
        } else {
            productDto = productManager.save(productDto);
        }
        return productDto;
    }

    public ProductDto getProductDto1(String name) throws Exception {

        ProductDto product1 = new ProductDto(name, "10000", "11000", "100",
                2384485L, "", "Machinery", "Metalworking",
                "UOM3434", "hsn3483948", "5", "5", "10", "Model 3454");

        return product1;

    }



    public void deleteProductById4(Long id) throws Exception  {}

    public void deleteProductById5(Long id) throws Exception  {}

    public void deleteProductById6(Long id) throws Exception  {}

}