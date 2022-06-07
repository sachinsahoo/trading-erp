package com.example.controller;

import com.example.common.exception.InputValidationException;
import com.example.constant.RsStatusType;
import com.example.controller.service.ProductService;
import com.example.controller.manager.ProductManager;
import com.example.entity.rs.order.ProductAddRequest;
import com.example.entity.rs.order.ProductListResponse;
import com.example.entity.rs.order.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    ProductManager productManager;

    @RequestMapping(value = "/save.ws", method = RequestMethod.POST)
    public ResponseEntity<?> save(@RequestBody ProductAddRequest prodRequest, HttpServletRequest request) {

        ProductResponse response = new ProductResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            prodRequest.validate();
            response.setProduct(productManager.save(prodRequest.getProduct()));


        } catch (InputValidationException e) {
            response.setErrorMessage(e.getError());
            response.setStatus(RsStatusType.FAILURE);

        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());

        }
        return new ResponseEntity<ProductResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/list.ws", method = RequestMethod.POST)
    public ResponseEntity<?> list(HttpServletRequest request) {

        ProductListResponse response = new ProductListResponse();
        response.setStatus(RsStatusType.SUCCESS);

        try {
            response.setProducts(productManager.getAllProducts());
        } catch (Exception e) {
            response.setStatus(RsStatusType.FAILURE);
            response.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
