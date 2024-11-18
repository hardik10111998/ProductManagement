package com.hardik.productmanagement.service;

import com.hardik.productmanagement.model.Product;
import com.hardik.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        // Mock data
        Product product1 = new Product(1L, "Product1", "Description1", 100.0, 10);
        Product product2 = new Product(2L, "Product2", "Description2", 200.0, 20);
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        // Execute
        List<Product> products = productService.getAllProducts();

        // Verify
        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Found() {
        // Mock data
        Product product = new Product(1L, "Product1", "Description1", 100.0, 10);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Execute
        Optional<Product> result = productService.getProductById(1L);

        // Verify
        assertTrue(result.isPresent());
        assertEquals("Product1", result.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_NotFound() {
        // Mock data
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute
        Optional<Product> result = productService.getProductById(1L);

        // Verify
        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct() {
        // Mock data
        Product product = new Product(null, "Product1", "Description1", 100.0, 10);
        Product savedProduct = new Product(1L, "Product1", "Description1", 100.0, 10);
        when(productRepository.save(product)).thenReturn(savedProduct);

        // Execute
        Product result = productService.createProduct(product);

        // Verify
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testUpdateProduct_Found() {
        // Mock data
        Product existingProduct = new Product(1L, "Product1", "Description1", 100.0, 10);
        Product updatedProduct = new Product(null, "UpdatedProduct", "UpdatedDescription", 150.0, 15);
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        // Execute
        Product result = productService.updateProduct(1L, updatedProduct);

        // Verify
        assertNotNull(result);
        assertEquals("UpdatedProduct", result.getName());
        assertEquals("UpdatedDescription", result.getDescription());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void testUpdateProduct_NotFound() {
        // Mock data
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Execute & Verify
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.updateProduct(1L, new Product(null, "Product1", "Description1", 100.0, 10));
        });

        assertEquals("Product not found", exception.getMessage());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteProduct_Found() {
        // Mock data
        when(productRepository.existsById(1L)).thenReturn(true);

        // Execute
        productService.deleteProduct(1L);

        // Verify
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        // Mock data
        when(productRepository.existsById(1L)).thenReturn(false);

        // Execute & Verify
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productService.deleteProduct(1L);
        });

        assertEquals("Product with id 1 not found", exception.getMessage());
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(0)).deleteById(anyLong());
    }
}
