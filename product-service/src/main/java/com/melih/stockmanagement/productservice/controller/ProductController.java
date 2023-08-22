package com.melih.stockmanagement.productservice.controller;

import com.melih.stockmanagement.productservice.enums.Language;
import com.melih.stockmanagement.productservice.exception.enums.FriendlyMessageCodes;
import com.melih.stockmanagement.productservice.exception.utils.FriendlyMessageUtils;
import com.melih.stockmanagement.productservice.repository.entity.Product;
import com.melih.stockmanagement.productservice.request.ProductCreateRequest;
import com.melih.stockmanagement.productservice.response.FriendlyMessage;
import com.melih.stockmanagement.productservice.response.InternalApiResponse;
import com.melih.stockmanagement.productservice.response.ProductResponse;
import com.melih.stockmanagement.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/product")
@RequiredArgsConstructor
class ProductController {
    private final IProductRepositoryService productRepositoryService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{language}/create")
    public InternalApiResponse<ProductResponse> createProduct (@PathVariable("language")Language language,
                                                               @RequestBody ProductCreateRequest productCreateRequest) {

        log.debug("[{}][createProduct] -> request: {}",this.getClass().getSimpleName(), productCreateRequest);
        Product product = productRepositoryService.createProduct(language, productCreateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][createProduct] -> response: {}",this.getClass().getSimpleName(),productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder().
                        title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .description(FriendlyMessageUtils.getFriendlyMessage(language,FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    private static ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .productCreatedDate(product.getProductCreatedDate().getTime())
                .productUpdatedDate(product.getProductUpdatedDate().getTime())
                .build();
    }
}
