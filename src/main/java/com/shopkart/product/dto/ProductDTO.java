package com.shopkart.product.dto;

import com.shopkart.product.entity.Attribute;
import com.shopkart.product.entity.Categories;
import com.shopkart.product.entity.Review;
import com.shopkart.product.entity.Sku;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String productId = UUID.randomUUID().toString();
    private String productName;
    private List<String> productImageURL;
    private String usp;
    private Categories category;
    private List<Sku> skus;
    private String description;
    private Attribute attribute;
    private List<Review> reviews;
    private Float ratings;
}
