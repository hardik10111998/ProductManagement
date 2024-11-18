package com.hardik.productmanagement.service;

import com.hardik.productmanagement.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing the service layer for managing products.
 * Provides methods to perform CRUD operations on products.
 */
public interface IProductService {
    /**
     * Retrieves a list of all available products.
     *
     * @return a list of {@link Product} objects.
     */
    public List<Product> getAllProducts();
    /**
     * Retrieves a product by its unique identifier.
     *
     * @param id the unique identifier of the product.
     * @return an {@link Optional} containing the {@link Product} if found,
     *         or an empty {@link Optional} if not found.
     */
    public Optional<Product> getProductById(Long id);
    /**
     * Creates a new product and stores it in the system.
     *
     * @param product the {@link Product} object to be created.
     * @return the created {@link Product} object.
     */
    public Product createProduct(Product product);

    /**
     * Updates an existing product identified by its unique identifier.
     *
     * @param id the unique identifier of the product to be updated.
     * @param updatedProduct the {@link Product} object containing updated information.
     * @return the updated {@link Product} object.
     */
    public Product updateProduct(Long id, Product updatedProduct);

    /**
     * delete a product by its unique identifier.
     *
     * @param id the unique identifier of the product.
     */
    public void deleteProduct(Long id);
}
