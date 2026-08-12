package com.sst.productservicesst.services;

import com.sst.productservicesst.dtos.FakeStoreProductDto;
import com.sst.productservicesst.exceptions.ProductNotFoundException;
import com.sst.productservicesst.models.Category;
import com.sst.productservicesst.models.Product;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService {

    @Override
    public Product getProductById(Long id) {

        // Call the FakeStore API to get the product with given id
        RestTemplate restTemplate = new RestTemplate();

        FakeStoreProductDto fakeStoreProductDto =
                restTemplate.getForObject(
                        "https://fakestoreapi.com/products/" + id,
                        FakeStoreProductDto.class
                );

        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException(
                    "Please pass a valid product id"
            );
        }

        // Convert FakeStoreProductDto to Product
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {

        RestTemplate restTemplate = new RestTemplate();

        FakeStoreProductDto[] fakeStoreProductDtos =
                restTemplate.getForObject(
                        "https://fakestoreapi.com/products",
                        FakeStoreProductDto[].class
                );

        List<Product> products = new ArrayList<>();

        if (fakeStoreProductDtos != null) {

            for (FakeStoreProductDto fakeStoreProductDto
                    : fakeStoreProductDtos) {

                products.add(
                        convertFakeStoreProductDtoToProduct(
                                fakeStoreProductDto
                        )
                );
            }
        }

        return products;
    }

    @Override
    public Product createProduct(Product product) {

        RestTemplate restTemplate = new RestTemplate();

        // Convert Product to FakeStoreProductDto
        FakeStoreProductDto fakeStoreProductDto =
                new FakeStoreProductDto();

        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImage());

        if (product.getCategory() != null) {
            fakeStoreProductDto.setCategory(
                    product.getCategory().getDescription()
            );
        }

        // Call FakeStore API
        FakeStoreProductDto response =
                restTemplate.postForObject(
                        "https://fakestoreapi.com/products",
                        fakeStoreProductDto,
                        FakeStoreProductDto.class
                );

        if (response == null) {
            throw new RuntimeException(
                    "Unable to create product"
            );
        }

        // Convert response back to Product
        return convertFakeStoreProductDtoToProduct(response);
    }

    private Product convertFakeStoreProductDtoToProduct(
            FakeStoreProductDto fakeStoreProductDto) {

        Product product = new Product();

        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImage(fakeStoreProductDto.getImage());

        Category category = new Category();
        category.setDescription(fakeStoreProductDto.getCategory());

        product.setCategory(category);

        return product;
    }
}