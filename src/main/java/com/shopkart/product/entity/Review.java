package com.shopkart.product.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Review {
    private String reviewId = UUID.randomUUID().toString();
    private String mId;
    private Float stars;
}