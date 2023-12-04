package com.shopkart.product.service.Impl;

import com.shopkart.product.entity.Categories;
import com.shopkart.product.entity.Product;
import com.shopkart.product.entity.Review;
import com.shopkart.product.entity.Sku;
import com.shopkart.product.repository.ProductRepository;
import com.shopkart.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Boolean AddProduct(Product product) throws ProductAddException {
        System.out.println(product.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
//        if (product.getProductId() != null) {
//            throw new IllegalArgumentException("Product ID should not be provided when adding a new product.");
//        }
        try {
            // Save the product to the repository
            productRepository.save(product);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new ProductAddException("Failed to add the product.", e);
        }
    }

    public Product getProductById(String productId) throws ProductNotFoundException {
        // Perform basic validation
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            return optionalProduct.get();
        } else {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    @Override
    public Boolean UpdateProduct(Product updatedProduct) throws ProductNotFoundException {
        if (updatedProduct == null || updatedProduct.getProductId() == null) {
            throw new IllegalArgumentException("Updated product and product ID must be provided.");
        }
        try {
            Product existingProduct = getProductById(updatedProduct.getProductId());
            existingProduct.setProductName(updatedProduct.getProductName());
            existingProduct.setProductImageURL(updatedProduct.getProductImageURL());
            existingProduct.setUsp(updatedProduct.getUsp());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setSkus(updatedProduct.getSkus());
            existingProduct.setDescription(updatedProduct.getDescription());
            existingProduct.setAttribute(updatedProduct.getAttribute());
            existingProduct.setReviews(updatedProduct.getReviews());
            existingProduct.setRatings(updatedProduct.getRatings());

            productRepository.save(existingProduct);

            return Boolean.TRUE;
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product with ID " + updatedProduct.getProductId() + " not found");
        }
    }

    @Override
    public Boolean DeleteProduct(String productId) throws ProductNotFoundException {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        try {
            Product existingProduct = getProductById(productId);
            productRepository.delete(existingProduct);

            return true;
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    public Boolean AddSKU(String productId, Sku sku) throws SKUAdditionException, ProductNotFoundException {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (sku == null || sku.getMId() == null) {
            throw new IllegalArgumentException("SKU and SKU ID must be provided.");
        }

        try {
            Product existingProduct = getProductById(productId);
            existingProduct.getSkus().add(sku);
            productRepository.save(existingProduct);
            return true;
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        } catch (Exception e) {
            throw new SKUAdditionException("Failed to add the SKU to the product.", e);
        }
    }

    @Override
    public Boolean UpdateSKUs(String productId, List<Sku> updatedSkus) throws ProductNotFoundException {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (updatedSkus == null || updatedSkus.isEmpty()) {
            throw new IllegalArgumentException("Updated SKUs list must be provided.");
        }
        try {
            Product existingProduct = getProductById(productId);
            existingProduct.setSkus(updatedSkus);
            productRepository.save(existingProduct);
            return Boolean.TRUE;
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    @Override
    public Boolean UpdateSKUbyMerchantIdProductId(String merchantId, String productId, Sku updatedSku) throws SKUUpdateException, ProductNotFoundException {
        if (merchantId == null || merchantId.isEmpty() || productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Merchant ID and Product ID cannot be null or empty.");
        }
        if (updatedSku == null || updatedSku.getMId() == null) {
            throw new IllegalArgumentException("Updated SKU and SKU ID must be provided.");
        }
        try {
            Product existingProduct = getProductByMerchantIdAndProductId(merchantId, productId);
            boolean skuUpdated = false;
            for (Sku sku : existingProduct.getSkus()) {
                if (sku.getMId().equals(updatedSku.getMId())) {
                    sku.setStock(updatedSku.getStock());
                    sku.setPrice(updatedSku.getPrice());
                    sku.setListingPrice(updatedSku.getListingPrice());
                    sku.setIsActive(updatedSku.getIsActive());
                    skuUpdated = true;
                    break;
                }
            }

            if (!skuUpdated) {
                throw new SKUUpdateException("SKU with ID " + updatedSku.getMId() + " not found in the product.");
            }

            productRepository.save(existingProduct);

            return true;
        } catch (ProductNotFoundException e) {
            throw new ProductNotFoundException("Product with ID " + productId + " not found");
        }
    }

    @Override
    public Boolean AddReview(String productId, Review newReview) throws ProductNotFoundException, ReviewAdditionException {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty.");
        }
        if (newReview == null || newReview.getReviewId() == null) {
            throw new IllegalArgumentException("Review and Review ID must be provided.");
        }
        try {
            Product existingProduct = getProductById(productId);

            // Add the new review to the product's reviews list
            existingProduct.getReviews().add(newReview);

            // Save the updated product
            productRepository.save(existingProduct);
            return true;
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new ReviewAdditionException("Failed to add the review to the product.", e);
        }
    }

    public List<Product> GetAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    @Override
    public List<Product> GetProductByCategory(Categories category) {
        List<Product> products = productRepository.findByCategory(category);
        return products;
    }

    @Override
    public Boolean updateStockByProductIdandMerchantId(String productId, String merchantId, Long stock , String what) {
        Product product = productRepository.findByMerchantIdAndProductId(merchantId,productId);
//        System.out.println(product);
        List<Sku> skuList = product.getSkus();
        System.out.println(skuList);
        for( Sku sku : skuList){
            if (sku.getMId().equals(merchantId)){
                Long stockGet = sku.getStock();
                if( what.equals("sold")){
                    stockGet = stockGet - stock;

                }else if (what.equals("cancelled")){
                    stockGet = stockGet + stock;

                }
                else {
                    continue;
                }
                sku.setStock(stockGet);
                break;
            }
        }
        productRepository.save(product);
        return true;
    }

    private Product getProductByMerchantIdAndProductId(String merchantId, String productId) throws ProductNotFoundException {

        if (merchantId == null || merchantId.isEmpty() || productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Merchant ID and Product ID cannot be null or empty.");
        }

        Product product = productRepository.findByMerchantIdAndProductId(merchantId, productId);

        if (product == null) {
            throw new ProductNotFoundException("Product not found for Merchant ID: " + merchantId + " and Product ID: " + productId);
        }

        return product;
    }

}
