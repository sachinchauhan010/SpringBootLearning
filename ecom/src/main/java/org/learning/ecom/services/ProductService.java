package org.learning.ecom.services;

import org.learning.ecom.models.Product;
import org.learning.ecom.payload.ProductDTO;
import org.learning.ecom.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductResponse getProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    ProductResponse getProductsByCategoryId(Long categoryId);
    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductDTO updateProduct(Long productId, ProductDTO newProductDTO);

    ProductResponse deleteProduct(Long productId);

    ProductDTO updateImage(Long productId, MultipartFile image) throws IOException;
}
