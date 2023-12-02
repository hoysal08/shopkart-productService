package com.shopkart.product.repository;

import com.shopkart.product.entity.Categories;
import com.shopkart.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'skus.mId': ?0, 'productId': ?1}")
    Product findByMerchantIdAndProductId(String merchantId, String productId);

    @Query("{'category': ?0}")
    List<Product> findByCategory(Categories category);
}
