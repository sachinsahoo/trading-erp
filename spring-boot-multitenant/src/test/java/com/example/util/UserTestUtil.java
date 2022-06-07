package com.example.util;

import com.example.controller.manager.ProductManager;
import com.example.controller.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTestUtil {
    @Autowired
    ProductManager productManager;

    @Autowired
    ProductService productService;

    // TODO
    public void createTenant(String name) throws Exception {

    }

    // TODO
    public void createUser(String name) throws Exception  {
    }

    public void deleteTenant(Long id) throws Exception {

    }

    public void deleteUser(Long id) throws Exception {

    }





    public void deleteProductById4(Long id) throws Exception  {}

    public void deleteProductById5(Long id) throws Exception  {}

    public void deleteProductById6(Long id) throws Exception  {}

}