package com.shopkart.product.feignclient;

import com.shopkart.product.dto.InventoryDto;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "employee-feign" , url = "http://localhost:8050" , fallbackFactory =  ProductFeignFallback.class)
public interface ProductFeign {

    @RequestMapping(method = RequestMethod.POST , value = "/inventory")
    ResponseEntity<Boolean> saveInInventory(@RequestBody InventoryDto inventoryDto);


}
