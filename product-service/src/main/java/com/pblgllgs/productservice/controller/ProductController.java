package com.pblgllgs.productservice.controller;

import com.pblgllgs.productservice.enums.Language;
import com.pblgllgs.productservice.exception.enums.FriendlyMessageCodes;
import com.pblgllgs.productservice.exception.utils.FriendlyMessageUtils;
import com.pblgllgs.productservice.repository.entity.Product;
import com.pblgllgs.productservice.request.ProductCreateRequest;
import com.pblgllgs.productservice.response.FriendlyMessage;
import com.pblgllgs.productservice.response.InternalApiResponse;
import com.pblgllgs.productservice.response.ProductResponse;
import com.pblgllgs.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final IProductRepositoryService iProductRepositoryService;

    @PostMapping("/{language}/create")
    @ResponseStatus(HttpStatus.CREATED)
    public InternalApiResponse<ProductResponse> createProduct(
            @RequestBody ProductCreateRequest productCreateRequest,
            @PathVariable("language") Language language
    ) {
        log.debug("[{}][createProduct] -> request: {}", this.getClass().getSimpleName(), productCreateRequest);
        Product product = iProductRepositoryService.createProduct(language, productCreateRequest);
        ProductResponse productResponse = getProductResponse(product);
        log.debug("[{}][createProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    private static ProductResponse getProductResponse(Product product) {
        return ProductResponse.builder()
                .productName(product.getProductName())
                .price(product.getPrice())
                .productId(product.getProductId())
                .quantity(product.getQuantity())
                .productCreatedDate(product.getProductCreatedDate().getTime())
                .productUpdatedDate(product.getProductUpdatedDate().getTime())
                .build();
    }
}
