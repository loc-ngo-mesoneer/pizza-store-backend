package io.locngo.pizza.store.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import io.locngo.pizza.store.product.domain.Product.ProductId;

public class ProductTest {

    @Test
    void givenValidInput_whenOf_thenReturnProduct() {
        final Product product = ProductTest.givenProduct();

        assertNotNull(product);
    }
    
    @Test
    void givenValidInput_whenNewInstance_thenReturnProduct() {
        final String expectedProductName = "Test Product Name";
        final double expectedPrice = 100.00;
        final long expectedQuantity = 99;
        final String expectedProductImageUrl = "Product Image Url";
        final String expectedDescription = "Product Description";
        final boolean expectedDisable = true;

        final Product product = Product.newInstance(
            expectedProductName, 
            expectedPrice, 
            expectedQuantity, 
            expectedProductImageUrl, 
            expectedDescription, 
            expectedDisable
        );

        assertNotNull(product);
        assertEquals(expectedProductName, product.getProductName());
        assertEquals(expectedPrice, product.getPrice());
        assertEquals(expectedQuantity, product.getQuantity());
        assertEquals(expectedProductImageUrl, product.getProductImageUrl());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedDisable, product.isDisable());
    }

    @Test
    void givenProduct_whenDisableProduct_thenReturnDisabledProduct() {
        final Product product = ProductTest.givenProduct();
        assertNotNull(product);
        final Product disabledProduct = product.disableProduct();

        assertNotNull(disabledProduct);
        assertEquals(true, disabledProduct.isDisable());
        assertEquals(product.getId(), disabledProduct.getId());
        assertEquals(product.getProductName(), disabledProduct.getProductName());
        assertEquals(product.getPrice(), disabledProduct.getPrice());
        assertEquals(product.getQuantity(), disabledProduct.getQuantity());
        assertEquals(product.getProductImageUrl(), disabledProduct.getProductImageUrl());
        assertEquals(product.getDescription(), disabledProduct.getDescription());
    }

    @Test
    void givenProduct_whenEnableProduct_thenEnabledProduct() {
        final Product product = ProductTest.givenProduct();
        assertNotNull(product);

        final Product enabledProduct = product.enableProduct();

        assertNotNull(enabledProduct);
        assertEquals(false, enabledProduct.isDisable());
        assertEquals(product.getId(), enabledProduct.getId());
        assertEquals(product.getProductName(), enabledProduct.getProductName());
        assertEquals(product.getPrice(), enabledProduct.getPrice());
        assertEquals(product.getQuantity(), enabledProduct.getQuantity());
        assertEquals(product.getProductImageUrl(), enabledProduct.getProductImageUrl());
        assertEquals(product.getDescription(), enabledProduct.getDescription());
    }

    @Test
    void testUpdateGeneralInformation() {
        final Product product = ProductTest.givenProduct();
        assertNotNull(product);
        final String expectedProductName = "Updated Product Name";
        final String expectedProductImageUrl = "Updated Product Image Url";
        final String expectedDescription = "Updated Description";

        final Product updatedProduct = product.updateGeneralInformation(
            expectedProductName, 
            expectedProductImageUrl, 
            expectedDescription
        );

        assertNotNull(updatedProduct);
        assertEquals(expectedProductName, updatedProduct.getProductName());
        assertEquals(expectedProductImageUrl, updatedProduct.getProductImageUrl());
        assertEquals(expectedDescription, updatedProduct.getDescription());
        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getPrice(), updatedProduct.getPrice());
        assertEquals(product.getQuantity(), updatedProduct.getQuantity());
        assertEquals(product.isDisable(), updatedProduct.isDisable());
    }

    @Test
    void givenValidInput_whenUpdatePrice_thenReturnUpdatedProduct() {
        final Product product = ProductTest.givenProduct();
        assertNotNull(product);
        final double expectedPrice = 79.99;
        
        final Product updatedProduct = product.updatePrice(expectedPrice);

        assertNotNull(updatedProduct);
        assertEquals(expectedPrice, updatedProduct.getPrice());
        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getProductName(), updatedProduct.getProductName());
        assertEquals(product.getQuantity(), updatedProduct.getQuantity());
        assertEquals(product.getProductImageUrl(), updatedProduct.getProductImageUrl());
        assertEquals(product.getDescription(), updatedProduct.getDescription());
        assertEquals(product.isDisable(), updatedProduct.isDisable());
    }

    @Test
    void givenValidInput_whenUpdateQuantity_thenReturnUpdatedProduct() {
        final Product product = ProductTest.givenProduct();
        assertNotNull(product);
        final long expectedQuantity = 199;

        final Product updatedProduct = product.updateQuantity(expectedQuantity);

        assertNotNull(updatedProduct);
        assertEquals(expectedQuantity, updatedProduct.getQuantity());
        assertEquals(product.getId(), updatedProduct.getId());
        assertEquals(product.getProductName(), updatedProduct.getProductName());
        assertEquals(product.getPrice(), updatedProduct.getPrice());
        assertEquals(product.getProductImageUrl(), updatedProduct.getProductImageUrl());
        assertEquals(product.getDescription(), updatedProduct.getDescription());
        assertEquals(product.isDisable(), updatedProduct.isDisable());
    }

    @Test
    void givenProductIsDisabled_whenCheckIsProductAvailable_thenReturnFalse() {
        final Product product = ProductTest.givenProduct();

        assertNotNull(product);

        final Product disabledProduct = product.disableProduct();

        assertNotNull(disabledProduct);
        assertEquals(true, disabledProduct.isDisable());
        assertEquals(false, disabledProduct.isProductAvailable(1));
    }

    @Test
    void givenProductWithQuantityExceeded_whenCheckIsProductAvailable_thenReturnFalse() {
        final Product product = ProductTest.givenProduct().enableProduct();
        
        assertNotNull(product);
        assertEquals(false, product.isDisable());
        
        final long maximumQuantity = product.getQuantity();

        assertEquals(false, product.isProductAvailable(maximumQuantity + 1));
    }

    @Test
    void givenProductIsEnableAndQuantityNotExceeded_whenCheckIsProductAvailable_thenReturnTrue() {
        final Product product = ProductTest.givenProduct().enableProduct();
        
        assertNotNull(product);
        assertEquals(false, product.isDisable());

        final long maximumQuantity = product.getQuantity();
        
        assertEquals(true, product.isProductAvailable(maximumQuantity));
        assertEquals(true, product.isProductAvailable(maximumQuantity - 1));
    }

    public static Product givenProduct() {
        final ProductId expectedProductId = ProductId.newInstance();
        final String expectedProductName = String.format(
            "Product %s", 
            expectedProductId.getValue()
        );    
        final double expectedPrice = 100.0;
        final long expectedQuantity = 9;
        final String expectedProductImageUrl = String.format(
            "Product image url ", 
            expectedProductId.getValue()
        );
        final String expectedDescription = String.format(
            "Product description %s", 
            expectedProductId.getValue()
        );
        final boolean expectedDisable = true;

        final Product product = Product.of(
            expectedProductId, 
            expectedProductName, 
            expectedPrice, 
            expectedQuantity, 
            expectedProductImageUrl, 
            expectedDescription, 
            expectedDisable
        );

        assertNotNull(product);
        assertEquals(expectedProductId, product.getId());
        assertEquals(expectedProductName, product.getProductName());
        assertEquals(expectedPrice, product.getPrice());
        assertEquals(expectedQuantity, product.getQuantity());
        assertEquals(expectedProductImageUrl, product.getProductImageUrl());
        assertEquals(expectedDescription, product.getDescription());
        assertEquals(expectedDisable, product.isDisable());

        return product;
    }
}
