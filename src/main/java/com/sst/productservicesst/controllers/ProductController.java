package com.sst.productservicesst.controllers;

import com.sst.productservicesst.models.Product;
import com.sst.productservicesst.services.FakeStoreProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// This controller is capable of hosting HTTP APIs
@RestController
@RequestMapping("/products")
public class ProductController {

    private final FakeStoreProductService fakeStoreProductService;

    public ProductController(FakeStoreProductService fakeStoreProductService) {
        this.fakeStoreProductService = fakeStoreProductService;
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        //throw new RuntimeException("Something went wrong")
//        ResponseEntity<Product> responseEntity = null;
//        Product product = null;
//        try{
//            product =fakeStoreProductService.getProductById(id);
//            responseEntity<Product> responseEntity = null;
//            System.out.println("Hello");
//            return responseEntity;
//        } catch(RuntimeException exception)
//        {
//            ExceptionDto dto = new ExceptionDto();
//            dto.setMessage("something went wrong");
//            ResponseEntity<ExceptionDto> response = new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
//            return response;
//        }
        return fakeStoreProductService.getProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return fakeStoreProductService.getAllProducts();
    }
}