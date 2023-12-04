package com.shopkart.product.feignclient;

import com.shopkart.product.dto.MerchantProductOfferingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "product-to-merchant-feign" , url = "http://localhost:8091" , fallbackFactory =  ProductToInventoryFeignFallback.class)
public interface ProductToMerchantFeign {

    @RequestMapping(method = RequestMethod.POST , value = "/merchant/{merchantId}/update-offerings")
    ResponseEntity<Boolean> updateOffering(@PathVariable("merchantId") String merchantId, @RequestBody List<MerchantProductOfferingDto> updateOfferingList);
}
