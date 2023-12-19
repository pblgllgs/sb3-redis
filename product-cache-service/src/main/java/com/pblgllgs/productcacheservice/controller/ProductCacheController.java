package com.pblgllgs.productcacheservice.controller;
/*
 *
 * @author pblgl
 * Created on 19-12-2023
 *
 */

import com.pblgllgs.productcacheservice.enums.Language;
import com.pblgllgs.productcacheservice.repository.entity.Product;
import com.pblgllgs.productcacheservice.response.InternalApiResponse;
import com.pblgllgs.productcacheservice.response.ProductResponse;
import com.pblgllgs.productcacheservice.service.ProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/product-cache")
public class ProductCacheController {

    private final ProductRepositoryService productRepositoryService;

    @GetMapping("/{language}/product-id/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public InternalApiResponse<ProductResponse> findProductById(
            @PathVariable("language") Language language,
            @PathVariable("productId") Long productId
    ) {
        log.debug("[{}][getProduct] -> request product id: {}", this.getClass().getSimpleName(), productId);
        Product product = productRepositoryService.getProduct(language, productId);
        ProductResponse productResponse = getProductResponse(product);
        log.debug("[{}][getProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }


    @DeleteMapping("/{language}/products")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProducts(@PathVariable("language") Language language
    ){
        productRepositoryService.deleteProducts(language);
    }

    private static ProductResponse getProductResponse(Product product) {
        return ProductResponse.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .productId(product.getProductId())
                .quantity(product.getQuantity())
                .productCreatedDate(product.getProductCreatedDate())
                .productUpdatedDate(product.getProductUpdatedDate())
                .build();
    }
}
