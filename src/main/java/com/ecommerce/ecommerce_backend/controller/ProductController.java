package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.ProductDTO;
import com.ecommerce.ecommerce_backend.dto.mapper.DTOMapper;
import com.ecommerce.ecommerce_backend.entity.Product;
import com.ecommerce.ecommerce_backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> add(@RequestBody Product product) {
        Product savedProduct = productService.addProduct(product);
        return ResponseEntity.ok(DTOMapper.toProductDTO(savedProduct));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(DTOMapper.toProductDTO(updatedProduct));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted");
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAll() {
        List<ProductDTO> products = productService.getAllProducts()
                .stream()
                .map(DTOMapper::toProductDTO)
                .toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> get(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(DTOMapper.toProductDTO(product));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> filterByCategory(@PathVariable String category) {
        List<ProductDTO> products = productService.getProductsByCategory(category)
                .stream()
                .map(DTOMapper::toProductDTO)
                .toList();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/maxprice/{price}")
    public ResponseEntity<List<ProductDTO>> filterByPrice(@PathVariable double price) {
        List<ProductDTO> products = productService.getProductsByPrice(price)
                .stream()
                .map(DTOMapper::toProductDTO)
                .toList();
        return ResponseEntity.ok(products);
    }
}
