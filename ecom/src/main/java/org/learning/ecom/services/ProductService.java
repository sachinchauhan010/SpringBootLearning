package org.learning.ecom.services;

import org.learning.ecom.models.Product;
import org.learning.ecom.payload.ProductDTO;
import org.learning.ecom.payload.ProductResponse;

public interface ProductService {

    ProductResponse getProducts();
    ProductResponse getProductsByCategoryId(Long categoryId);
    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO addProduct(Long categoryId, Product product);

    ProductDTO updateProduct(Long productId, Product newProduct);
}
