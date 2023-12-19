package com.pblgllgs.productservice.service.impl;
/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */

import com.pblgllgs.productservice.enums.Language;
import com.pblgllgs.productservice.exception.enums.FriendlyMessageCodes;
import com.pblgllgs.productservice.exception.exceptions.ProductAlreadyDeletedException;
import com.pblgllgs.productservice.exception.exceptions.ProductNotCreatedException;
import com.pblgllgs.productservice.exception.exceptions.ProductNotFoundException;
import com.pblgllgs.productservice.repository.ProductRepository;
import com.pblgllgs.productservice.repository.entity.Product;
import com.pblgllgs.productservice.request.ProductCreateRequest;
import com.pblgllgs.productservice.request.ProductUpdateRequest;
import com.pblgllgs.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryServiceImpl implements IProductRepositoryService {

    private final ProductRepository productRepository;

    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        try {
            Product product = Product.builder()
                    .productName(productCreateRequest.getProductName())
                    .quantity(productCreateRequest.getQuantity())
                    .price(productCreateRequest.getPrice())
                    .deleted(false)
                    .build();
            Product responseProduct = productRepository.save(product);
            log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), responseProduct);
            return responseProduct;
        } catch (Exception ex) {
            throw new ProductNotCreatedException(
                    language,
                    FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION,
                    "product request: " + productCreateRequest.toString()
            );
        }
    }

    @Override
    public Product getProduct(Language language, Long productId) {
        log.debug("[{}][getProduct] -> request productId: {}", this.getClass().getSimpleName(), productId);
        Product product = productRepository.findByProductIdAndDeletedFalse(productId);
        if (product == null) {
            throw new ProductNotFoundException(
                    language,
                    FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION,
                    "Product not found for product id: " + productId
            );
        }
        log.debug("[{}][getProduct] -> response: {}", this.getClass().getSimpleName(), product);
        return product;
    }

    @Override
    public List<Product> getProducts(Language language) {
        log.debug("[{}][getProducts] -> request products", this.getClass().getSimpleName());
        List<Product> products = productRepository.findAllByDeletedFalse();
        if (products == null) {
            throw new ProductNotFoundException(
                    language,
                    FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION,
                    "Products not found"
            );
        }
        log.debug("[{}][getProducts] -> response: {}", this.getClass().getSimpleName(), products);
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Language language, Long productId, ProductUpdateRequest productUpdateRequest) {
        log.debug("[{}][updateProduct] -> request productUpdateRequest: {}", this.getClass().getSimpleName(), productUpdateRequest);
        Product product = getProduct(language, productId);
        product.setProductName(productUpdateRequest.getProductName());
        product.setQuantity(productUpdateRequest.getQuantity());
        product.setPrice(productUpdateRequest.getPrice());
        product.setProductCreatedDate(product.getProductCreatedDate());
        product.setProductUpdatedDate(new Date());
        Product productResponse = productRepository.save(product);
        log.debug("[{}][updateProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return productResponse;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        log.debug("[{}][deleteProduct] -> request delete product with id: {}", this.getClass().getSimpleName(), productId);
        Product product;
        try {
            product = getProduct(language, productId);
            product.setDeleted(true);
            product.setProductUpdatedDate(new Date());
            Product productResponse = productRepository.save(product);
            log.debug("[{}][deleteProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
            return product;
        } catch (ProductAlreadyDeletedException exception) {
            throw new ProductNotCreatedException(
                    language,
                    FriendlyMessageCodes.PRODUCT_ALREADY_DELETED,
                    "Product already deleted, id: " + productId
            );
        }
    }
}
