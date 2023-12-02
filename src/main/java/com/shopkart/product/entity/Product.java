package com.shopkart.product.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
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
