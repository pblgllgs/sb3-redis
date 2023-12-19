package com.pblgllgs.productcacheservice.service;
/*
 *
 * @author pblgl
 * Created on 18-12-2023
 *
 */

import com.pblgllgs.productcacheservice.enums.Language;
import com.pblgllgs.productcacheservice.repository.entity.Product;

public interface ProductRepositoryService {

    Product getProduct(Language language, Long productId);

    void deleteProducts(Language language);
}
