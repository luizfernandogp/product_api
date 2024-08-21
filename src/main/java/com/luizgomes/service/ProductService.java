package com.luizgomes.service;

import com.luizgomes.model.Product;
import com.luizgomes.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product createProduct(Product order) {
        return productRepository.save(order);
    }

    public Product updateProduct(Long id, Product updateProduct) {
        return productRepository.findById(id).map(order -> {
            order.setDescription(updateProduct.getDescription());
            order.setName(updateProduct.getName());
            order.setPrice(updateProduct.getPrice());
            return productRepository.save(order);
        }).orElse(null);
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
