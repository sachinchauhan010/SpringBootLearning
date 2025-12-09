package org.learning.ecom.services;

import org.learning.ecom.exceptions.ResourceNotFound;
import org.learning.ecom.models.Category;
import org.learning.ecom.models.Product;
import org.learning.ecom.payload.ProductDTO;
import org.learning.ecom.payload.ProductResponse;
import org.learning.ecom.repositories.CategoryRepository;
import org.learning.ecom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductResponse getProducts(){
        List<Product> allProducts= productRepository.findAll();

        List<ProductDTO> productsDto= allProducts.stream().map((product)-> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productsDto);

        return productResponse;
    }


    @Override
    public ProductResponse getProductsByCategoryId(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFound("category","CategoryId", categoryId));

        List<Product> allProducts= productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productsDto= allProducts.stream().map((product)-> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productsDto);

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword){

        List<Product> allProducts= productRepository.findByProductNameLikeIgnoreCase("%"+keyword+"%");
        List<ProductDTO> productsDto= allProducts.stream().map((product)-> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productsDto);

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, Product newProduct){
        Product product= productRepository.findByProductId(productId);
        product.setProductName(newProduct.getProductName());
        product.setPrice(newProduct.getPrice());
        product.setCategory(newProduct.getCategory());
        product.setDiscount(newProduct.getDiscount());
        product.setDescription(newProduct.getDescription());
        product.setImage(newProduct.getImage());
        product.setQuantity(newProduct.getQuantity());
        product.setSpecialPrice(newProduct.getPrice()- (newProduct.getPrice()* (newProduct.getDiscount()/100)));
        productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO addProduct(Long categoryId, Product product) {
        Category category = categoryRepository. findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category", "category id", categoryId));

        product.setCategory(category);
        product.setImage("default.png");
        double discount =product.getPrice()*(product.getDiscount()*0.01);
        System.out.println("Discount is: "+ discount);
        product.setSpecialPrice(product.getPrice()- discount);
        Product savedProduct = productRepository.save(product);
        System.out.println("Saved product is: "+ savedProduct);
        return modelMapper.map(savedProduct, ProductDTO.class);

    }

    @Override
    public ProductResponse deleteProduct(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("product", "productID", productId));
        productRepository.delete(product);

        List<Product> allProducts= productRepository.findAll();
        List<ProductDTO> productDtos= allProducts.stream().map(product1 ->  modelMapper.map(product1, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        return productResponse;
    }
}
