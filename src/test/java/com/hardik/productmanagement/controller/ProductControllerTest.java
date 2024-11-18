package com.hardik.productmanagement.controller;

import com.hardik.productmanagement.model.Product;
import com.hardik.productmanagement.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest {

    @Mock
    private IProductService productService;

    @InjectMocks
    private ProductController productController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    void testGetAllProducts() throws Exception {
        // Mock data
        Product product1 = new Product(1L, "Product1", "Description1", 100.0, 10);
        Product product2 = new Product(2L, "Product2", "Description2", 200.0, 20);
        when(productService.getAllProducts()).thenReturn(Arrays.asList(product1, product2));

        // Perform GET request
        mockMvc.perform(get("/api/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Product1"))
                .andExpect(jsonPath("$[1].name").value("Product2"));

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void testGetProductById_Found() throws Exception {
        // Mock data
        Product product = new Product(1L, "Product1", "Description1", 100.0, 10);
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));

        // Perform GET request
        mockMvc.perform(get("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Product1"));

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(1L)).thenReturn(Optional.empty());

        // Perform GET request
        mockMvc.perform(get("/api/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void testCreateProduct() throws Exception {
        // Mock data
        Product product = new Product(null, "Product1", "Description1", 100.0, 10);
        Product createdProduct = new Product(1L, "Product1", "Description1", 100.0, 10);
        when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);

        // Perform POST request
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Product1",
                                    "description": "Description1",
                                    "price": 100.0,
                                    "quantity": 10
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Product1"));

        verify(productService, times(1)).createProduct(any(Product.class));
    }

    @Test
    void testUpdateProduct() throws Exception {
        // Mock data
        Product updatedProduct = new Product(1L, "UpdatedProduct", "UpdatedDescription", 150.0, 15);
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);

        // Perform PUT request
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "UpdatedProduct",
                                    "description": "UpdatedDescription",
                                    "price": 150.0,
                                    "quantity": 15
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("UpdatedProduct"));

        verify(productService, times(1)).updateProduct(eq(1L), any(Product.class));
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Perform DELETE request
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isNoContent());

        verify(productService, times(1)).deleteProduct(1L);
    }
}
