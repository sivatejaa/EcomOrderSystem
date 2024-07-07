package com.myproject.product_service.service;

import com.myproject.product_service.dto.ProductRequest;
import com.myproject.product_service.dto.ProductResponse;
import com.myproject.product_service.model.Product;
import com.myproject.product_service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {


    private final ProductRepository productRepository;
// Using final keyword - Thread Safety: No need for synchronization because productRepository is assigned once and never changes.
//Consistency: The ProductService is always in a consistent state with a properly initialized productRepository.
//No Accidental Changes: The productRepository cannot be changed accidentally after the ProductService is created.

    //Automatically injects without the need of @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();


        log.info("Product {} is saved", product.getId());
        return productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                        .map(this::mapToProductResponse)
                        .toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
