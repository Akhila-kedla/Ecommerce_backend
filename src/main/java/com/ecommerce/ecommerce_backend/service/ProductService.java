package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.entity.Product;
import com.ecommerce.ecommerce_backend.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductService {

    private static final Logger logger = Logger.getLogger(ProductService.class.getName());

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product addProduct(Product product) {

        logger.info("Adding new product: " + product.getName());
        Product savedProduct = productRepository.save(product);

        logger.info("Product added successfully with ID: " + savedProduct.getId());
        return savedProduct;
    }


    public Product updateProduct(Long id, Product updatedProduct) {

        logger.info("Updating product with ID: " + id);

        Product existingProduct = getProductById(id);

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStock(updatedProduct.getStock());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setImageurl(updatedProduct.getImageurl());
        existingProduct.setRating(updatedProduct.getRating());

        Product savedProduct = productRepository.save(existingProduct);

        logger.info("Product updated successfully with ID: " + savedProduct.getId());
        return savedProduct;
    }


    public void deleteProduct(Long id) {

        logger.warning("Deleting product with ID: " + id);
        productRepository.deleteById(id);
    }


    public Product getProductById(Long id) {

        logger.info("Fetching product with ID: " + id);

        return productRepository.findById(id)
                .orElseThrow(() -> {
                    logger.severe("Product not found with ID: " + id);
                    return new ResourceNotFoundException("Product not found");
                });
    }


    public List<Product> getAllProducts() {

        logger.info("Fetching all products");
        return productRepository.findAll();
    }


    public List<Product> getProductsByCategory(String category) {

        logger.info("Fetching products by category: " + category);
        return productRepository.findByCategory(category);
    }


    public List<Product> getProductsByPrice(double price) {

        logger.info("Fetching products with price <= " + price);
        return productRepository.findByPriceLessThanEqual(price);
    }
}
