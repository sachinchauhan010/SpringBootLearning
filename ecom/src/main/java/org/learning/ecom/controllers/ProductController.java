package org.learning.ecom.controllers;

import org.apache.coyote.Response;
import org.learning.ecom.models.Product;
import org.learning.ecom.payload.ProductDTO;
import org.learning.ecom.payload.ProductResponse;
import org.learning.ecom.services.ProductService;
import org.learning.ecom.services.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getProducts(){
        ProductResponse productResponse = productService.getProducts();
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/{categoryId}")
    public ResponseEntity<ProductResponse> getProducts(@PathVariable Long categoryId){
        ProductResponse productResponse = productService.getProductsByCategoryId(categoryId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){
        ProductResponse productResponse= productService.getProductsByKeyword(keyword);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PostMapping("/admin/categories/{categoryId}/add-product")
    public ResponseEntity<ProductDTO> addProduct( @PathVariable Long categoryId, @RequestBody ProductDTO productDTO){
        ProductDTO response=productService.addProduct(categoryId, productDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/admin/product/{productId}/update")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO newProductDTO){
        ProductDTO response= productService.updateProduct(productId, newProductDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/admin/product/{productId}/delete")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long productId){
        ProductResponse productResponse = productService.deleteProduct(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @PutMapping("/admin/product/{productId}/update-image")
    public ResponseEntity<ProductDTO> uploadImage (@PathVariable Long productId,
                                                   @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO updatedProduct= productService.updateImage(productId, image);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

    }


}
