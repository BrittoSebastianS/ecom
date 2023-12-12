package com.ecom.productService.service;

import com.ecom.productService.dto.ProductRequest;
import com.ecom.productService.dto.ProductResponse;
import com.ecom.productService.model.Product;
import com.ecom.productService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> mapToProductResponse(product)).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();

        return productResponse;
    }

    public ProductResponse getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        ProductResponse productResponse = ProductResponse.builder()
                .id(product.get().getId())
                .name(product.get().getName())
                .description(product.get().getDescription())
                .price(product.get().getPrice())
                .build();

        return productResponse;
    }
}
