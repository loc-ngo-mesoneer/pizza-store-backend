package io.locngo.pizza.store.order.domain;

import java.util.UUID;

import io.locngo.pizza.store.common.validation.ApiValidation;
import io.locngo.pizza.store.product.domain.Product;
import io.locngo.pizza.store.product.domain.Product.ProductId;
import lombok.Getter;

@Getter
public class OrderItem {
    
    private final OrderItemId id;

    private final ProductId productId;

    private final String productName;

    private final double price;

    private final long quantity;

    private OrderItem(
        final OrderItemId id,
        final ProductId productId,
        final String productName,
        final double price,
        final long quantity
    ) {
        ApiValidation.requireNonNull(id, "id");
        ApiValidation.requireNonNull(productId, "productId");
        ApiValidation.requireStringNonBlank(productName, "productName");
        ApiValidation.requireGreaterOrEqualThanZero(price, "price");
        ApiValidation.requireGreaterOrEqualThanZero(Double.valueOf(quantity), "quantity");

        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItem newInstance(final Product product, final long quantity) {
        ApiValidation.requireNonNull(product, "product");
        ApiValidation.requirePostiveNumber(Double.valueOf(quantity), "quantity");

        if(!product.isProductAvailable(quantity)) {
            throw new IllegalArgumentException(
                String.format(
                    "New Instance of OrderItem failed because product %s is not available! [remaining products: %s, requesting products: %s]", 
                    product.getId().getValue(),
                    product.getQuantity(),
                    quantity
                )
            );
        }

        return new OrderItem(
            OrderItemId.newInstance(), 
            product.getId(), 
            product.getProductName(), 
            product.getPrice(), 
            quantity
        );
    }

    public static OrderItem of(
        final OrderItemId id,
        final ProductId productId,
        final String productName,
        final double price,
        final long quantity
    ) {
        return new OrderItem(id, productId, productName, price, quantity);
    }

    public double getTotalPrice() {
        return this.price * this.quantity;
    }

    public OrderItem addQuantity(final long amount) {
        ApiValidation.requirePostiveNumber(Double.valueOf(amount), "amount");

        final long updatedQuantity = this.quantity + amount;

        return new OrderItem(
            this.id, 
            this.productId, 
            this.productName, 
            this.price, 
            updatedQuantity
        );
    }

    public OrderItem substractQuantity(final long amount) {
        ApiValidation.requirePostiveNumber(Double.valueOf(amount), "amount");
    
        final long caculatedQuantity = this.quantity - amount;

        final long updatedQuantity = caculatedQuantity < 0 
            ? 0
            : (this.quantity - amount);

        return new OrderItem(
            this.id, 
            this.productId, 
            this.productName, 
            this.price, 
            updatedQuantity
        );
    }

    public boolean isEmpty() {
        return this.quantity <= 0;
    }

    @Getter
    public static class OrderItemId {
        
        private final UUID value;

        private OrderItemId(final UUID value) {
            this.value = value;
        }

        public static OrderItemId newInstance() {
            return new OrderItemId(UUID.randomUUID());
        }

        public static OrderItemId fromString(final String value) {
            return new OrderItemId(UUID.fromString(value));
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
            OrderItemId other = (OrderItemId) obj;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }
    }
}
