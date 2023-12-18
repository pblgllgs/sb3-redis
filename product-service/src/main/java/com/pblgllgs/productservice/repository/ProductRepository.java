package com.pblgllgs.productservice.repository;

import com.pblgllgs.productservice.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Product findByProductIdAndDeletedFalse(Long productId);
    List<Product> findAllByDeletedFalse();
}
