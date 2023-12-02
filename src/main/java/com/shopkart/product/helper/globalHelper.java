package com.shopkart.product.helper;

import com.shopkart.product.dto.ProductDTO;
import com.shopkart.product.entity.Product;
import org.springframework.beans.BeanUtils;

public class globalHelper {
    public static ProductDTO ProductToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }

    public static Product ProductDTOToProduct(ProductDTO productDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
