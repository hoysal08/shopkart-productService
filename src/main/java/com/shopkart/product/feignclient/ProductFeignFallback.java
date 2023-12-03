package com.shopkart.product.feignclient;

import com.shopkart.product.dto.InventoryDto;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
public class ProductFeignFallback implements FallbackFactory<ProductFeign> {
    @Override
    public ProductFeign create(Throwable throwable) {
        return new ProductFeign() {

            @Override
            public ResponseEntity<Boolean> saveInInventory(InventoryDto inventoryDto) {
                return new ResponseEntity<>(Boolean.FALSE,HttpStatus.OK);
            }
        };
    }
}
