package com.shopkart.product.feignclient;

import com.shopkart.product.dto.InventoryDto;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ProductFeignFallback implements FallbackFactory<ProductToInventoryFeign> {
    @Override
    public ProductToInventoryFeign create(Throwable throwable) {
        return new ProductToInventoryFeign() {

            @Override
            public ResponseEntity<Boolean> saveInInventory(InventoryDto inventoryDto) {
                return new ResponseEntity<>(Boolean.FALSE,HttpStatus.OK);
            }

            @Override
            public ResponseEntity<Boolean> saveInventoryMultiple(List<InventoryDto> inventoryDtos) {
                return new ResponseEntity<>(Boolean.FALSE,HttpStatus.OK);
            }
        };
    }
}
