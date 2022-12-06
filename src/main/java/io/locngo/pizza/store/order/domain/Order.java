package io.locngo.pizza.store.order.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import io.locngo.pizza.store.common.validation.ApiValidation;
import io.locngo.pizza.store.product.domain.Product;
import io.locngo.pizza.store.product.domain.Product.ProductId;
import lombok.Getter;

@Getter
public class Order {

    private final OrderId id;

    private final String phoneNumber;

    private final String address;

    private final OrderStatus status;

    private final Map<OrderStatus, OrderStage> stages;

    private final Map<ProductId, OrderItem> orderItems;

    private final String description;

    private Order(
            final OrderId id,
            final String phoneNumber,
            final String address,
            final OrderStatus status,
            final Collection<OrderStage> stages,
            final Collection<OrderItem> orderItems,
            final String description
    ) {
        ApiValidation.requireNonNull(id, "id");
        ApiValidation.requireStringNonBlank(phoneNumber, "phoneNumber");
        ApiValidation.requireStringNonBlank(address, "address");
        ApiValidation.requireNonNull(status, "status");
        ApiValidation.requireNonNull(stages, "stages");
        ApiValidation.requireNonNull(orderItems, "orderItems");

        this.id = id;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.status = status;
        this.stages = stages.stream().collect(
            Collectors.toMap(OrderStage::getStatus, Function.identity())
        );
        this.orderItems = orderItems.stream().collect(
            Collectors.toMap(OrderItem::getProductId, Function.identity())
        );
        this.description = description;
    }

    public static Order of(
            final OrderId id,
            final String phoneNumber,
            final String address,
            final OrderStatus status,
            final Collection<OrderStage> stages,
            final Collection<OrderItem> orderItems,
            final String description
    ) {
        return new Order(
            id, 
            phoneNumber, 
            address, 
            status,
            stages, 
            orderItems,
            description
        );
    }

    public Map<OrderStatus, OrderStage> getStages() {
        return Collections.unmodifiableMap(this.stages);
    }

    public Map<ProductId, OrderItem> getOrderItems() {
        return Collections.unmodifiableMap(this.orderItems);
    }

    public Order addOrderStage(final OrderStage stage) {
        ApiValidation.requireNonNull(stage, "stage");

        if (this.stages.containsKey(stage.getStatus())) {
            throw new IllegalArgumentException(
                String.format(
                    "Add order stage failed because status %s already existed", 
                    stage.getStatus()
                )
            );
        }

        this.stages.put(stage.getStatus(), stage);

        return new Order(
            this.id, 
            this.phoneNumber, 
            this.address, 
            stage.getStatus(),
            this.stages.values(), 
            this.orderItems.values(),
            this.description
        );
    }

    public Order addOrderItem(
        final Product product,
        final long amount
    ) {
        ApiValidation.requireNonNull(product, "product");
        ApiValidation.requirePostiveNumber(Double.valueOf(amount), "amount");

        final OrderItem updatedOrderItem = this.updateOrderItemToBeAdded(product, amount);

        this.orderItems.put(updatedOrderItem.getProductId(), updatedOrderItem);

        return new Order(
            this.id, 
            this.phoneNumber, 
            this.address,
            this.status,
            this.stages.values(), 
            this.orderItems.values(), 
            description
        );
    }

    private OrderItem updateOrderItemToBeAdded(
        final Product product, 
        final long amount
    ) {
        final OrderItem existedOrderItem = this.orderItems.get(product.getId());

        if(Objects.isNull(existedOrderItem)) {
            return OrderItem.newInstance(product, amount);
        }

        final OrderItem updatedOrderItem = existedOrderItem.addQuantity(amount);

        if (!product.isProductAvailable(updatedOrderItem.getQuantity())) {
            throw new IllegalArgumentException(
                String.format(
                    "Substract order item failed because product %s is not available! [remaining products: %s, requesting product %s]", 
                    product.getId().getValue(),
                    amount
                )
            );
        }

        return updatedOrderItem;
    }

    public Order substractOrderItem(
        final Product product,
        final long amount
    ) {
        ApiValidation.requireNonNull(product, "product");
        ApiValidation.requirePostiveNumber(Double.valueOf(amount), "amount");

        final OrderItem subtractedOrderItem = this.updateOrderItemToBeSubtracted(product, amount);

        if (subtractedOrderItem.isEmpty()) {
            this.orderItems.remove(subtractedOrderItem.getProductId());
        } else {
            this.orderItems.put(subtractedOrderItem.getProductId(), subtractedOrderItem);
        }
        
        return new Order(
            this.id, 
            this.phoneNumber, 
            this.address, 
            this.status,
            this.stages.values(), 
            this.orderItems.values(), 
            this.description
        );
    }

    private OrderItem updateOrderItemToBeSubtracted(
        final Product product,
        final long amount
    ) {
        final OrderItem existedOrderItem = this.orderItems.get(product.getId());

        if(Objects.isNull(existedOrderItem)) {
            throw new NoSuchElementException(
                String.format(
                    "Substract order item failed because order item not existed with product id %s",
                    product.getId().getValue()
                )
            );
        }

        final OrderItem subtractedOrderItem = existedOrderItem.substractQuantity(amount);

        if(product.isProductAvailable(subtractedOrderItem.getQuantity())) {
            throw new IllegalArgumentException(
                String.format(
                    "Substract order item failed because product %s is not available!, [remaining products: %s, requesting product %s]", 
                    product.getId().getValue(),
                    amount
                )
            );
        }

        return subtractedOrderItem;
    }

    @Getter
    public static class OrderId {

        private final UUID value;

        private OrderId(final UUID value) {
            this.value = value;
        }

        public static OrderId newInstance() {
            return new OrderId(UUID.randomUUID());
        }

        public static OrderId fromString(final String value) {
            return new OrderId(UUID.fromString(value));
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
            OrderId other = (OrderId) obj;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }
    }
}
