package com.pblgllgs.productservice.controller;

import com.pblgllgs.productservice.enums.Language;
import com.pblgllgs.productservice.exception.enums.FriendlyMessageCodes;
import com.pblgllgs.productservice.exception.utils.FriendlyMessageUtils;
import com.pblgllgs.productservice.repository.entity.Product;
import com.pblgllgs.productservice.request.ProductCreateRequest;
import com.pblgllgs.productservice.request.ProductUpdateRequest;
import com.pblgllgs.productservice.response.FriendlyMessage;
import com.pblgllgs.productservice.response.InternalApiResponse;
import com.pblgllgs.productservice.response.ProductResponse;
import com.pblgllgs.productservice.service.IProductRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @Operation(description = "Save all product")
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

    @Operation(description = "Get all products")
    @GetMapping("/{language}/product-id/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public InternalApiResponse<ProductResponse> findProductById(
            @PathVariable("language") Language language,
            @PathVariable("productId") Long productId
    ) {
        log.debug("[{}][getProduct] -> request product id: {}", this.getClass().getSimpleName(), productId);
        Product product = iProductRepositoryService.getProduct(language, productId);
        ProductResponse productResponse = getProductResponse(product);
        log.debug("[{}][getProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @Operation(description = "Update product by id")
    @PutMapping("/{language}/product-id/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public InternalApiResponse<ProductResponse> updateProductById(
            @PathVariable("language") Language language,
            @PathVariable("productId") Long productId,
            @RequestBody ProductUpdateRequest productUpdateRequest
    ) {
        log.debug("[{}][updateProduct] -> request update product with id: {}", this.getClass().getSimpleName(), productId);
        Product product = iProductRepositoryService.getProduct(language, productId);
        Product productUpdated = iProductRepositoryService.updateProduct(language, product.getProductId(), productUpdateRequest);
        ProductResponse productResponse = getProductResponse(productUpdated);
        log.debug("[{}][updateProduct] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @Operation(description = "Get all products")
    @GetMapping("/{language}")
    @ResponseStatus(HttpStatus.OK)
    public InternalApiResponse<List<ProductResponse>> findAllProducts(
            @PathVariable("language") Language language
    ) {
        log.debug("[{}][getProducts] -> request products", this.getClass().getSimpleName());
        List<Product> products = iProductRepositoryService.getProducts(language);
        List<ProductResponse> productResponse = getProductsResponse(products);
        log.debug("[{}][getProducts] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<List<ProductResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @Operation(description = "Delete product by id")
    @DeleteMapping("/{language}/product-id/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public InternalApiResponse<ProductResponse> deleteProductById(
            @PathVariable("language") Language language,
            @PathVariable("productId") Long productId
    ) {
        log.debug("[{}][deleteProduct] -> request delete product with id: {}", this.getClass().getSimpleName(), productId);
        Product product = iProductRepositoryService.getProduct(language, productId);
        Product deletedProduct = iProductRepositoryService.deleteProduct(language, product.getProductId());
        ProductResponse productResponse = getProductResponse(deletedProduct);
        log.debug("[{}][deleteProduct] -> response", this.getClass().getSimpleName());
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
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

    private static List<ProductResponse> getProductsResponse(List<Product> products) {
        return products
                .stream()
                .map(ProductController::getProductResponse)
                .collect(Collectors.toList()
                );
    }
}
