package io.locngo.pizza.store.order.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import io.locngo.pizza.store.order.domain.OrderItem.OrderItemId;
import io.locngo.pizza.store.product.domain.Product;
import io.locngo.pizza.store.product.domain.Product.ProductId;

public class OrderItemTest {

    @Test
    void givenValidInput_whenOf_thenReturnOrderItem() {
        final OrderItemId expectedId = OrderItemId.newInstance();
        final ProductId expectedProductId = ProductId.newInstance();
        final String expectedProductName = String.format(
            "Product %s", 
            expectedProductId.getValue()
        );
        final double expectedPrice = 100.00;
        final long expectedQuantity = 9;
        
        final OrderItem orderItem = OrderItem.of(
            expectedId, 
            expectedProductId, 
            expectedProductName, 
            expectedPrice, 
            expectedQuantity
        );

        assertNotNull(orderItem);
        assertEquals(expectedId, orderItem.getId());
        assertEquals(expectedProductId, orderItem.getProductId());
        assertEquals(expectedProductName, orderItem.getProductName());
        assertEquals(expectedPrice, orderItem.getPrice());
        assertEquals(expectedQuantity, orderItem.getQuantity());
    }

    @Test
    void givenValidInput_whenNewInstance_thenReturnOrderItem() {
        final OrderItem orderItem = OrderItemTest.givenOrderItem();
        
        assertNotNull(orderItem);
    }

    @Test
    void givenOrderItem_whenGetTotalPrice_thenReturnResult() {
        final OrderItem orderItem = OrderItemTest.givenOrderItem();
        assertNotNull(orderItem);

        final double expectedResult = orderItem.getPrice() * orderItem.getQuantity();

        assertEquals(expectedResult, orderItem.getTotalPrice());
    }

    @Test
    void givenProductAndQuantity_whenNewInstance_thenReturnOrderItem() {
        final Product product = Product.of(
            ProductId.newInstance(), 
            "Test Product Name", 
            199.99, 
            99, 
            "Test Product Image Url", 
            "Test Description", 
            false
        );
        final long amount = 1;
        
        final OrderItem orderItemWith1 = OrderItem.newInstance(product, amount);
        
        assertNotNull(orderItemWith1);
        assertEquals(amount, orderItemWith1.getQuantity());
        assertNotNull(orderItemWith1.getId());
        assertEquals(product.getId(), orderItemWith1.getProductId());
        assertEquals(product.getProductName(), orderItemWith1.getProductName());
        assertEquals(product.getPrice(), orderItemWith1.getPrice());


        final long maximumQuantity = product.getQuantity();
        
        final OrderItem orderItemWithMaximum = OrderItem.newInstance(product, maximumQuantity);

        assertNotNull(orderItemWithMaximum);
        assertEquals(maximumQuantity, orderItemWithMaximum.getQuantity());
        assertNotNull(orderItemWithMaximum.getId());
        assertEquals(product.getId(), orderItemWithMaximum.getProductId());
        assertEquals(product.getProductName(), orderItemWithMaximum.getProductName());
        assertEquals(product.getPrice(), orderItemWithMaximum.getPrice());
    }

    @Test
    void givenProductExceededQuantity_whenNewInstance_thenThrowException() {
        final Product product = Product.of(
            ProductId.newInstance(), 
            "Test Product Name", 
            199.99, 
            99, 
            "Test Product Image Url", 
            "Test Description", 
            false
        );
        final long exceededAmount = product.getQuantity() + 1;
        
        assertThrows(
            IllegalArgumentException.class, 
            () -> {
                OrderItem.newInstance(product, exceededAmount);
            },
            String.format(
                "Product %s is not available! [remaining products: %d, requesting products: %d]", 
                product.getId().getValue(),
                product.getQuantity(),
                exceededAmount
            )
        );        

    }

    @Test
    void givenOrderItem_whenAddQuantity_thenReturnUpdatedOrderItem() {
        final OrderItem orderItem = OrderItemTest.givenOrderItem();
        assertNotNull(orderItem);
        final long amount = 9;
        final long expectedQuantity = orderItem.getQuantity() + amount;

        final OrderItem addedOrderItem = orderItem.addQuantity(amount);

        assertNotNull(addedOrderItem);
        assertEquals(expectedQuantity, addedOrderItem.getQuantity());
        assertEquals(orderItem.getId(), addedOrderItem.getId());
        assertEquals(orderItem.getProductId(), addedOrderItem.getProductId());
        assertEquals(orderItem.getProductName(), addedOrderItem.getProductName());
        assertEquals(orderItem.getPrice(), addedOrderItem.getPrice());
    }

    @Test
    void givenOrderItemAndAmountLessThanOrEqualQuantity_whenSubstractQuantity_thenReturnUpdatedOrderItem() {
        final OrderItem orderItem = OrderItemTest.givenOrderItem();
        assertNotNull(orderItem);
        assertTrue(orderItem.getQuantity() > 0);
        final long amount = 1;
        final long expectedRemainingQuantity = orderItem.getQuantity() - amount;

        final OrderItem subtractedOrderItemWith1 = orderItem.substractQuantity(amount);

        assertNotNull(subtractedOrderItemWith1);
        assertEquals(expectedRemainingQuantity, subtractedOrderItemWith1.getQuantity());
        assertEquals(orderItem.getId(), subtractedOrderItemWith1.getId());
        assertEquals(orderItem.getProductId(), subtractedOrderItemWith1.getProductId());
        assertEquals(orderItem.getProductName(), subtractedOrderItemWith1.getProductName());
        assertEquals(orderItem.getPrice(), subtractedOrderItemWith1.getPrice());


        final long maximumQuantity = orderItem.getQuantity();

        final OrderItem subtractedOrderItemWithMaximumQuantity = orderItem.substractQuantity(maximumQuantity);

        assertNotNull(subtractedOrderItemWithMaximumQuantity);
        assertEquals(0, subtractedOrderItemWithMaximumQuantity.getQuantity());
        assertEquals(orderItem.getId(), subtractedOrderItemWithMaximumQuantity.getId());
        assertEquals(orderItem.getProductId(), subtractedOrderItemWithMaximumQuantity.getProductId());
        assertEquals(orderItem.getProductName(), subtractedOrderItemWithMaximumQuantity.getProductName());
        assertEquals(orderItem.getPrice(), subtractedOrderItemWithMaximumQuantity.getPrice());
    }

    @Test
    void givenSubtractingAmountGreaterThanCurrentQuantity_whenSubstractQuantity_thenReturnThrowException() {
        final OrderItem orderItem = OrderItemTest.givenOrderItem();
        assertNotNull(orderItem);
        assertTrue(orderItem.getQuantity() > 0);
        final long exceededAmount = orderItem.getQuantity() + 1;

        final OrderItem exceededOrderItem = orderItem.substractQuantity(exceededAmount);

        assertNotNull(exceededOrderItem);
        assertEquals(0, exceededOrderItem.getQuantity());
        assertEquals(orderItem.getId(), exceededOrderItem.getId());
        assertEquals(orderItem.getProductId(), exceededOrderItem.getProductId());
        assertEquals(orderItem.getProductName(), exceededOrderItem.getProductName());
        assertEquals(orderItem.getPrice(), exceededOrderItem.getPrice());
    }

    @Test
    void givenOrderItem_whenIsEmpty_thenReturnCorrectResult() {
        final OrderItem emptyOrderItem = OrderItem.of(
            OrderItemId.newInstance(), 
            ProductId.newInstance(), 
            "Test Product Name", 
            199.99, 
            0
        );
        assertNotNull(emptyOrderItem);
        assertEquals(0, emptyOrderItem.getQuantity());
        assertTrue(emptyOrderItem.isEmpty());

        final OrderItem notEmptyOrderItem = OrderItem.of(
            OrderItemId.newInstance(), 
            ProductId.newInstance(), 
            "Test Product Name", 
            199.99, 
            99
        );
        assertNotNull(notEmptyOrderItem);
        assertTrue(notEmptyOrderItem.getQuantity() > 0);
        assertFalse(notEmptyOrderItem.isEmpty());
    }

    public static OrderItem givenOrderItem() {
        final OrderItemId expectedId = OrderItemId.newInstance();
        final ProductId expectedProductId = ProductId.newInstance();
        final String expectedProductName = String.format(
            "Product %s",
            expectedProductId.getValue()
        );
        final double expectedPrice = 100.0;
        final long expectedQuantity = 9;

        final OrderItem orderItem = OrderItem.of(
            expectedId,
            expectedProductId, 
            expectedProductName, 
            expectedPrice, 
            expectedQuantity
        );

        assertNotNull(orderItem);
        assertEquals(expectedId, orderItem.getId());
        assertEquals(expectedProductId, orderItem.getProductId());
        assertEquals(expectedProductName, orderItem.getProductName());
        assertEquals(expectedPrice, orderItem.getPrice());
        assertEquals(expectedQuantity, expectedQuantity);

        return orderItem;
    }
}
