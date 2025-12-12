package org.learning.ecom.services;

import org.learning.ecom.exceptions.APIException;
import org.learning.ecom.exceptions.ResourceNotFound;
import org.learning.ecom.models.Category;
import org.learning.ecom.models.Product;
import org.learning.ecom.payload.ProductDTO;
import org.learning.ecom.payload.ProductResponse;
import org.learning.ecom.repositories.CategoryRepository;
import org.learning.ecom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;


    @Override
    public ProductResponse getProducts(){
        List<Product> allProducts= productRepository.findAll();

        if(allProducts.isEmpty()){
            throw new APIException("No products found");
        }

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
        if(allProducts.isEmpty()){
            throw new APIException("No product Match");
        }
        List<ProductDTO> productsDto= allProducts.stream().map((product)-> modelMapper.map(product, ProductDTO.class)).toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productsDto);

        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO newProductDTO){
        Product product= productRepository.findByProductId(productId);
        if(product == null){
            throw new ResourceNotFound("product","ProductId", productId);
        }
        Product newProduct= modelMapper.map(newProductDTO,Product.class);
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
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFound("Category", "category id", categoryId));

        boolean isPresentProduct = false;
        List<Product> products = category.getProducts();

        for (Product product : products) {
            if (product.getProductName().equals(productDTO.getProductName())) {
                isPresentProduct = true;
                break;
            }
        }
        if (isPresentProduct) {
            throw new APIException("product already exists");
        }

        Product product = modelMapper.map(productDTO, Product.class);
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

    @Override
    public ProductDTO updateImage(Long productId, MultipartFile image) throws IOException {
        //1. Find the Product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFound("product", "productID", productId));
        //2. Upload the image to server and Get the file name or url from server
        String fileName= fileService.uploadImage(path, image);
        //3. Update the image field with the url in product
        product.setImage(fileName);

        //4. Save the Product
        Product updatedProduct = productRepository.save(product);

        //5. return the Product DTO
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

}
