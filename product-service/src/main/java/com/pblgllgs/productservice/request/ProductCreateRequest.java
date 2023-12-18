package com.pblgllgs.productservice.request;
/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */

import lombok.Data;

@Data
public class ProductCreateRequest {

    private String productName;
    private Integer quantity;
    private Double price;

}
