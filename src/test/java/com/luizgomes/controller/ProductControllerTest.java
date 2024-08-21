package com.luizgomes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatchException;
import com.luizgomes.data.converter.ProductConverter;
import com.luizgomes.data.request.ProductDTO;
import com.luizgomes.model.Product;
import com.luizgomes.service.ProductService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private ProductConverter productConverter;

    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService, productConverter);
    }

    @Test
    public void testGetAllProducts() {
        Product product = new Product();
        List<Product> products = Collections.singletonList(product);
        when(productService.getAllProducts()).thenReturn(products);

        List<Product> response = productController.getAllProducts();
        verify(productService).getAllProducts();
        assertEquals(response, products);
    }

    @Test
    public void testGetProductById() {
        Product product = new Product();
        when(productService.getProductById(anyLong())).thenReturn(product);

        Product response = productController.getProductById(1L);
        verify(productService).getProductById(anyLong());
        assertEquals(response, product);
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(productService.getProductById(anyLong())).thenReturn(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> productController.getProductById(1L));
        assertEquals(exception.getStatusCode(),HttpStatus.NOT_FOUND);
        assertEquals(exception.getReason(), "Product with ID 1 not found");
    }

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();
        when(productConverter.toProduct(productDTO)).thenReturn(product);
        when(productService.createProduct(product)).thenReturn(product);

        Product response = productController.createProduct(productDTO);
        verify(productService).createProduct(any(Product.class));
        assertEquals(response, product);
    }

    @Test
    public void testUpdateProduct() throws JsonPatchException, JsonProcessingException {
        ProductDTO productDTO = new ProductDTO();
        Product existingProduct = new Product();
        Product updatedProduct = new Product();
        when(productService.getProductById(anyLong())).thenReturn(existingProduct);
        when(productConverter.toProduct(productDTO)).thenReturn(updatedProduct);
        when(productService.updateProduct(anyLong(), any(Product.class))).thenReturn(updatedProduct);

        Product response = productController.updateProduct(1L, productDTO);
        verify(productService).updateProduct(anyLong(), any(Product.class));
        assertEquals(response, updatedProduct);
    }

    @Test
    public void testUpdateProductNotFound() throws JsonPatchException, JsonProcessingException {
        ProductDTO productDTO = new ProductDTO();
        when(productService.getProductById(anyLong())).thenReturn(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> productController.updateProduct(1L, productDTO));
        assertEquals(exception.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(exception.getReason(), "Product with ID 1 not found");
    }

    @Test
    public void testDeleteProduct() {
        when(productService.deleteProduct(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = productController.deleteProduct(1L);
        verify(productService).deleteProduct(anyLong());
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    public void testDeleteProductNotFound() {
        when(productService.deleteProduct(anyLong())).thenReturn(false);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> productController.deleteProduct(1L));
        assertEquals(exception.getStatusCode(), HttpStatus.NOT_FOUND);
        assertEquals(exception.getReason(), "Product with ID 1 not found");
    }
}