package org.learning.ecom.services;

import org.learning.ecom.models.Product;
import org.learning.ecom.payload.ProductDTO;

public interface ProductService {
    ProductDTO addProduct(Long categoryId, Product product);
}
