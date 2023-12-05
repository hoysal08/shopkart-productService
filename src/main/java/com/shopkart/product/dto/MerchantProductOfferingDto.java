package com.shopkart.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MerchantProductOfferingDto {
    private String productId;
    private Boolean isActive;
    private Long numberSold;
}
