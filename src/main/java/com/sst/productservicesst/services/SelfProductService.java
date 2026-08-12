package com.sst.productservicesst.services;

import com.sst.productservicesst.exceptions.ProductNotFoundException;
import com.sst.productservicesst.models.Product;
import com.sst.productservicesst.repositories.ProductRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class SelfProductService implements ProductService {

    private final ProductRepository productRepository;

    public SelfProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Long id) {

        Optional<Product> optionalProduct =
                productRepository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(
                    "Product Not Found: " + id
            );
        }

        return optionalProduct.get();
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}