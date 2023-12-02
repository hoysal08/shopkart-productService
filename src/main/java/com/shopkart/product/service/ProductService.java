package com.shopkart.product.service;


import com.shopkart.product.entity.Categories;
import com.shopkart.product.entity.Product;
import com.shopkart.product.entity.Review;
import com.shopkart.product.entity.Sku;
import com.shopkart.product.service.Impl.*;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProductService {
    Boolean AddProduct(Product product) throws ProductAddException;

    Product getProductById(String productId) throws ProductNotFoundException;

    Boolean UpdateProduct(Product updatedProduct) throws ProductNotFoundException;

    Boolean DeleteProduct(String productId) throws ProductNotFoundException;

    Boolean AddSKU(String productId, Sku sku) throws SKUAdditionException, ProductNotFoundException;

    Boolean UpdateSKUs(String productId, List<Sku> updatedSku) throws ProductNotFoundException;

    Boolean UpdateSKUbyMerchantIdProductId(String merchantId, String productId, Sku updatedSku) throws SKUUpdateException, ProductNotFoundException;

    Boolean AddReview(String productId, Review newReview) throws ProductNotFoundException, ReviewAdditionException;

    List<Product> GetAllProducts();

    List<Product> GetProductByCategory(Categories category);
}
