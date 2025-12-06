package org.learning.ecom.controllers;

import org.learning.ecom.models.Product;
import org.learning.ecom.payload.ProductDTO;
import org.learning.ecom.payload.ProductResponse;
import org.learning.ecom.services.ProductService;
import org.learning.ecom.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/add-product")
    public ResponseEntity<ProductDTO> addProduct( @PathVariable Long categoryId, @RequestBody Product product){
        ProductDTO response=productService.addProduct(categoryId, product);
        System.out.println("Added product is: "+ response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
