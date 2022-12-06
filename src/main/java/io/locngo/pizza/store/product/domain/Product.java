package io.locngo.pizza.store.product.domain;

import java.util.UUID;

import io.locngo.pizza.store.common.validation.ApiValidation;
import lombok.Getter;

@Getter
public class Product {
    
    private final ProductId id;

    private final String productName;

    private final double price;

    private final long quantity;

    private final String productImageUrl;

    private final String description;

    private final boolean disable;

    private Product(
        final ProductId id,
        final String productName,
        final double price,
        final long quantity,
        final String productImageUrl,
        final String description,
        final boolean disable
    ) {
        ApiValidation.requireNonNull(id, "id");
        ApiValidation.requireStringNonBlank(productName, "productName");
        ApiValidation.requireGreaterOrEqualThanZero(price, "price");
        ApiValidation.requireGreaterOrEqualThanZero(Double.valueOf(quantity), "quantity");
        ApiValidation.requireStringNonBlank(productImageUrl, "productImageUrl");

        this.id = id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.productImageUrl = productImageUrl;
        this.description = description;
        this.disable = disable;
    }

    public static Product of(
        final ProductId id,
        final String productName,
        final double price,
        final long quantity,
        final String productImageUrl,
        final String description,
        final boolean disable
    ) {
        return new Product(
            id, 
            productName, 
            price, 
            quantity, 
            productImageUrl, 
            description, 
            disable
        );
    }

    public static Product newInstance(
        final String productName,
        final double price,
        final long quantity,
        final String productImageUrl,
        final String description,
        final boolean disable
    ) {
        return new Product(
            ProductId.newInstance(), 
            productName, 
            price, 
            quantity, 
            productImageUrl, 
            description, 
            disable
        );
    }

    public Product updatePrice(final double price) {
        ApiValidation.requireGreaterOrEqualThanZero(price, "price");

        return new Product(
            this.id, 
            this.productName, 
            price, 
            this.quantity, 
            this.productImageUrl, 
            this.description, 
            this.disable
        );
    }

    public Product updateQuantity(final long quantity) {
        ApiValidation.requireGreaterOrEqualThanZero(Double.valueOf(quantity), "quantity");

        return new Product(
            this.id, 
            this.productName, 
            this.price, 
            quantity, 
            this.productImageUrl, 
            this.description, 
            this.disable
        );
    }

    public Product updateGeneralInformation(
        final String productName,
        final String productImageUrl,
        final String description
    ) {
        ApiValidation.requireStringNonBlank(productName, "productName");
        ApiValidation.requireStringNonBlank(productImageUrl, "productImageUrl");

        return new Product(
            this.id, 
            productName, 
            this.price, 
            this.quantity, 
            productImageUrl, 
            description, 
            this.disable
        );
    }

    public Product disableProduct() {
        return new Product(
            id, 
            productName, 
            price, 
            quantity, 
            productImageUrl, 
            description, 
            true
        );
    }

    public Product enableProduct() {
        return new Product(
            id, 
            productName, 
            price, 
            quantity, 
            productImageUrl, 
            description, 
            false
        );
    }

    public boolean isProductAvailable(final long amount) {
        if (this.disable) {
            return false;
        }

        if(this.quantity - amount < 0) {
            return false;
        }

        return true;
    }

    @Getter
    public static class ProductId {
        
        private final UUID value;

        private ProductId(final UUID value) {
            this.value = value;
        } 

        public static ProductId newInstance() {
            return new ProductId(UUID.randomUUID());
        }

        public static ProductId fromString(final String value) {
            return new ProductId(UUID.fromString(value));
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            ProductId other = (ProductId) obj;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }        
    }
}
