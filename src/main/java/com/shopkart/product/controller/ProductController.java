package com.shopkart.product.controller;

import com.shopkart.product.dto.ProductDTO;
import com.shopkart.product.dto.ProductIdDto;
import com.shopkart.product.entity.Categories;
import com.shopkart.product.entity.Product;
import com.shopkart.product.entity.Review;
import com.shopkart.product.entity.Sku;
import com.shopkart.product.helper.globalHelper;
import com.shopkart.product.service.Impl.*;
import com.shopkart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = globalHelper.ProductDTOToProduct(productDTO);
            boolean isAdded = productService.AddProduct(product);
            if (isAdded) {
                return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to add the product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ProductAddException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = globalHelper.ProductDTOToProduct(productDTO);
            boolean isAdded = productService.UpdateProduct(product);
            if (isAdded) {
                return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to add the product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String productId) {
        try {
            Product product = productService.getProductById(productId);
            ProductDTO productDTO = globalHelper.ProductToProductDTO(product);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productId) {
        try {
            boolean isDeleted = productService.DeleteProduct(productId);
            if (isDeleted) {
                return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to delete the product", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{productId}/add-sku")
    public ResponseEntity<String> addSKU(@PathVariable String productId, @RequestBody Sku sku) {
        try {
            boolean isAdded = productService.AddSKU(productId, sku);
            if (isAdded) {
                return new ResponseEntity<>("SKU added successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to add the SKU", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (SKUAdditionException | ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{productId}/update-skus")
    public ResponseEntity<String> updateSKUs(@PathVariable String productId, @RequestBody List<Sku> updatedSkus) {
        try {
            boolean isUpdated = productService.UpdateSKUs(productId, updatedSkus);
            if (isUpdated) {
                return new ResponseEntity<>("SKUs updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update the SKUs", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{merchantId}/{productId}/update-sku")
    public ResponseEntity<String> updateSKUbyMerchantIdProductId(
            @PathVariable String merchantId, @PathVariable String productId, @RequestBody Sku updatedSku) {
        try {
            boolean isUpdated = productService.UpdateSKUbyMerchantIdProductId(merchantId, productId, updatedSku);
            if (isUpdated) {
                return new ResponseEntity<>("SKU updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to update the SKU", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (SKUUpdateException | ProductNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{productId}/add-review")
    public ResponseEntity<String> addReview(@PathVariable String productId, @RequestBody Review newReview) {
        try {
            boolean isAdded = productService.AddReview(productId, newReview);
            if (isAdded) {
                return new ResponseEntity<>("Review added successfully", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to add the review", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ProductNotFoundException | ReviewAdditionException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.GetAllProducts();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product: products){
            productDTOS.add(globalHelper.ProductToProductDTO(product));
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @GetMapping("/by-category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Categories category) {
        List<Product> products = productService.GetProductByCategory(category);
        List<ProductDTO> productDTOS = new ArrayList<>();
        for(Product product: products){
            productDTOS.add(globalHelper.ProductToProductDTO(product));
        }
        return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }

    @PostMapping("/byprodIds")
    public ResponseEntity<List<ProductDTO>> getProdsbyprodIds (@RequestBody List<ProductIdDto> prodIds){
        return  ResponseEntity.ok(productService.getProductsbyProductIds(prodIds));
    }

}
