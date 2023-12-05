package com.shopkart.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDto {
    private String productId;
    private String merchantId;
    private Long stock;
    private Float price;
    private Float listingPrice;
    private Boolean isActive;
}
