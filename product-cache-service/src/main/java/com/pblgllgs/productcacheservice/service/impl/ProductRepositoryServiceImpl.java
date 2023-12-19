package com.pblgllgs.productcacheservice.service.impl;
/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */

import com.pblgllgs.productcacheservice.enums.Language;
import com.pblgllgs.productcacheservice.feign.product.ProductServiceFeignClient;
import com.pblgllgs.productcacheservice.repository.ProductRepository;
import com.pblgllgs.productcacheservice.repository.entity.Product;
import com.pblgllgs.productcacheservice.response.ProductResponse;
import com.pblgllgs.productcacheservice.service.ProductRepositoryService;
import feign.FeignException;
import feign.FeignException.NotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryServiceImpl implements ProductRepositoryService {

    private final ProductRepository productRepository;
    private final ProductServiceFeignClient productServiceFeignClient;

    @Override
    public Product getProduct(Language language, Long productId) {
        Product product;
        try {
            Optional<Product> optionalProduct = productRepository.findById(productId);
            if (optionalProduct.isPresent()) {
                product = optionalProduct.get();
            } else {
                log.info("Data came from product-service");
                product = new Product();
                ProductResponse response = productServiceFeignClient.getProductById(language, productId).getPayload();
                product.setProductId(response.getProductId());
                product.setProductName(response.getProductName());
                product.setPrice(response.getPrice());
                product.setQuantity(response.getQuantity());
                product.setProductCreatedDate(response.getProductCreatedDate());
                product.setProductUpdatedDate(response.getProductUpdatedDate());
                productRepository.save(product);
            }
        } catch (FeignException.FeignClientException.NotFound ex) {
            throw new NotFoundException("Product not found for product id: " + productId);
        }
        return product;
    }

    @Override
    public void deleteProducts(Language language) {
        productRepository.deleteAll();
        log.info("Delete all products");
    }
}
