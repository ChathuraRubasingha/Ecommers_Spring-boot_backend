package com.sample.ecommers.controller;

import com.sample.ecommers.exception.ProductNotFoundException;
import com.sample.ecommers.model.Products;
import com.sample.ecommers.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/createproduct")
    Products newproduct(@RequestBody Products newproduct){
        return productRepository.save(newproduct);
    }

    @GetMapping("/getproducts")
    List<Products> getallproducts (){
        return productRepository.findAll();
    }

    @GetMapping("/getproductbyid/{id}")
    Products getProductById(@PathVariable Long id){
        return productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
    }
}
