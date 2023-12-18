package com.pblgllgs.productservice.service.impl;
/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */

import com.pblgllgs.productservice.enums.Language;
import com.pblgllgs.productservice.exception.enums.FriendlyMessageCodes;
import com.pblgllgs.productservice.exception.exceptions.ProductNotCreatedException;
import com.pblgllgs.productservice.repository.ProductRepository;
import com.pblgllgs.productservice.repository.entity.Product;
import com.pblgllgs.productservice.request.ProductCreateRequest;
import com.pblgllgs.productservice.request.ProductUpdateRequest;
import com.pblgllgs.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                    "product request: "+ productCreateRequest.toString()
            );
        }
    }

    @Override
    public Product getProduct(Language language, Long productId) {
        return null;
    }

    @Override
    public List<Product> getProducts(Language language) {
        return null;
    }

    @Override
    public Product updateProduct(Language language, Long productId, ProductUpdateRequest productUpdateRequest) {
        return null;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        return null;
    }
}
