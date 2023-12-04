package com.shopkart.product.feignclient;

import com.shopkart.product.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "product-to-inventory-feign" , url = "http://localhost:8050" , fallbackFactory = ProductToInventoryFeignFallback.class)
public interface ProductToInventoryFeign {

    @RequestMapping(method = RequestMethod.POST , value = "/inventory")
    ResponseEntity<Boolean> saveInInventory(@RequestBody InventoryDto inventoryDto);

    @RequestMapping(method = RequestMethod.POST , value = "/inventory/saveMultiple")
    ResponseEntity<Boolean> saveInventoryMultiple(@RequestBody List<InventoryDto> inventoryDtos);


}
