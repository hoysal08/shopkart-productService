package com.shopkart.product.feignclient;

import com.shopkart.product.dto.InventoryDto;
import com.shopkart.product.dto.MerchantProductOfferingDto;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProductToMerchantFeignFallback implements FallbackFactory<ProductToMerchantFeign> {


    @Override
    public ProductToMerchantFeign create(Throwable throwable) {
        return new ProductToMerchantFeign() {

            @Override
            public ResponseEntity<Boolean> updateOffering(String merchantId, List<MerchantProductOfferingDto> updateOfferingList) {
                return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
            }
        };
    }
}