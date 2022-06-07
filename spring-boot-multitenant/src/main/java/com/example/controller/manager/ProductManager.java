package com.example.controller.manager;


import com.example.common.exception.InputValidationException;
import com.example.controller.service.ProductService;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.mapper.ProductMapper;
import com.example.entity.order.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductManager extends BaseManager {

    @Autowired
    HttpSession session;

    @Autowired
    private ProductService productService;

    public ProductDto save(ProductDto productDto) throws Exception {

        productDto.setTenantid(getUserTenant());
        if (productDto.getId() == null && productService.findByProductName(productDto.getName()) != null) {
            throw new InputValidationException("Product with  name: " + productDto.getName() + "already exists");
        }
        productDto = ProductMapper.mapperEntityToDto.apply(
                productService.saveProduct(productDto));
        return productDto;
    }

    public List<ProductDto> getAllProducts() throws Exception {
        productService.updateProductAvailableStock();
        productService.updatePendingStock();
        List<Product> products = productService.getAllProducts();
        List<ProductDto> prods = products.stream().map(ProductMapper.mapperEntityToDto).collect(Collectors.toList());
        return prods;
    }


}
