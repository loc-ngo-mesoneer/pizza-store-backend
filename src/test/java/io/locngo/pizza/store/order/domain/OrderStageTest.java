// package io.locngo.pizza.store.order.domain;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertNotEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.time.LocalDateTime;
// import java.util.UUID;

// import org.junit.jupiter.api.Test;

// import io.locngo.pizza.store.common.constant.OrderConstant;
// import io.locngo.pizza.store.common.constant.RoleConstant;
// import io.locngo.pizza.store.order.domain.OrderStage.OrderStageId;
// import io.locngo.pizza.store.user.domain.User;
// import io.locngo.pizza.store.user.domain.UserTest;
// import io.locngo.pizza.store.user.domain.User.UserId;

// public class OrderStageTest {

//     @Test
//     void givenValidInput_whenOf_thenReturnOrderStage() {
//         final OrderStageId expectedId = OrderStageId.newInstance();
//         final String expectedAssigneeId = UUID.randomUUID().toString();
//         final String expectedAssigneeFirstname = "test_firstname";
//         final String expectedAssigneeLastname = "test_lastname";
//         final OrderStatus expectedStatus = OrderStatus.PLACED;
//         final LocalDateTime expectedCreatedAt = LocalDateTime.now();

//         final OrderStage stage = OrderStage.of(
//             expectedId, 
//             expectedAssigneeId, 
//             expectedAssigneeFirstname, 
//             expectedAssigneeLastname, 
//             expectedStatus, 
//             expectedCreatedAt
//         );

//         assertNotNull(stage);
//         assertEquals(expectedId, stage.getId());
//         assertEquals(expectedAssigneeId, stage.getAssigneeId());
//         assertEquals(expectedAssigneeFirstname, stage.getAssigneeFirstname());
//         assertEquals(expectedAssigneeLastname, stage.getAssigneeLastName());
//         assertEquals(expectedStatus, stage.getStatus());
//         assertNotNull(stage.getCreatedAt());
//     }

//     @Test
//     void givenValidInput_whenPlaced_thenReturnOrderStage() {
//         final String expectedCustomerPhoneNumber = "0123456789";
        
//         final OrderStage placeStage = OrderStage.placed(expectedCustomerPhoneNumber);
        
//         assertNotNull(placeStage);
//         assertNotNull(placeStage.getId());
//         assertEquals(OrderConstant.CUSTOMER, placeStage.getAssigneeFirstname());
//         assertEquals(OrderConstant.CUSTOMER, placeStage.getAssigneeLastName());
//         assertEquals(OrderStatus.PLACED, placeStage.getStatus());
//         assertNotNull(placeStage.getCreatedAt());
//     }

//     @Test
//     void givenValidInput_whenConfirmed_thenReturnOrderStage() {
//         final OrderStage confirmStage = OrderStageTest.givenConfirmedStage();

//         assertNotNull(confirmStage);
//     }

//     @Test
//     void givenNonReceptionistUser_whenConfirmed_thenThrowException() {
//         final OrderStage placedOrder = OrderStageTest.givenPlacedStage();
//         final User chef = UserTest.givenChef();
//         assertNotNull(placedOrder);
//         assertNotNull(chef);
//         assertEquals(OrderStatus.PLACED, placedOrder.getStatus());
//         assertEquals(chef.getRoles().contains(RoleConstant.RECEPTIONIST));

//         assertThrows(
//             IllegalStateException.class, 
//             () -> {
//                 OrderStage.confirmed(placedOrder, chef);
//             },
//             String.format(
//                 "OrderStage transition to %s failed because invalid role %s", 
//                 OrderStatus.CONFIRMED,
//                 chef.getRoles()
//             )
//         );
//     }

//     @Test
//     void givenInvalidStatus_whenConfirmed_thenThrowException() {
//         final OrderStage cookedStage = OrderStageTest.givenCookedStage();
//         final User receptionist = UserTest.givenReceptionist();
//         assertNotNull(cookedStage);
//         assertNotNull(receptionist);
//         assertNotEquals(OrderStatus.PLACED, cookedStage.getStatus());
//         assertTrue(receptionist.getRoles().contains(RoleConstant.RECEPTIONIST));

//         assertThrows(
//             IllegalStateException.class, 
//             () -> {
//                 OrderStage.confirmed(cookedStage, receptionist);
//             },
//             String.format(
//                 "OrderStage transition to %s failed because invalid status %s",
//                 OrderStatus.CONFIRMED,
//                 cookedStage.getStatus()
//             )
//         );
        
//     }

//     @Test
//     void givenValidInput_whenCooked_thenReturnOrderStage() {
//        final OrderStage cookedStage = OrderStageTest.givenCookedStage();

//        assertNotNull(cookedStage);
//     }

//     @Test
//     void givenNonChefUser_whenCooked_thenThrowException() {
//         final OrderStage confirmedStage = OrderStageTest.givenConfirmedStage();
//         final User receptionist = UserTest.givenReceptionist();
//         assertNotNull(confirmedStage);
//         assertNotNull(receptionist);
//         assertFalse(receptionist.getRoles().contains(RoleConstant.CHEF));
//         assertEquals(OrderStatus.CONFIRMED, confirmedStage.getStatus());

//         assertThrows(
//             IllegalStateException.class, 
//             () -> {
//                 OrderStage.cooked(confirmedStage, receptionist);
//             },
//             String.format(
//                 "OrderStage transition to %s failed because invalid role %s", 
//                 OrderStatus.COOKED,
//                 receptionist.getRoles()
//             )
//         );
//     }

//     @Test
//     void givenInvalidStatus_whenCooked_thenThrowException() {
//         final OrderStage placedStage = OrderStageTest.givenPlacedStage();
//         final User chef = UserTest.givenChef();
//         assertNotNull(placedStage);
//         assertNotNull(chef);
//         assertNotEquals(OrderStatus.COOKED, placedStage.getStatus());
//         assertTrue(chef.getRoles().contains(RoleConstant.CHEF));

//         assertThrows(
//             IllegalStateException.class, 
//             () -> {
//                 OrderStage.cooked(placedStage, chef);
//             },
//             String.format(
//                 "OrderStage transition to %s failed because invalid status %s",
//                 OrderStatus.COOKED,
//                 placedStage.getStatus()
//             )
//         );
//     }

//     @Test
//     void givenValidInput_whenDelivered_thenReturnOrderStage() {
//         final OrderStage deliveredStage = OrderStageTest.givenDeliveredStage();

//         assertNotNull(deliveredStage);
//     }

//     @Test
//     void givenNonShipperUser_whenDelivered_thenThrowException() {
//         final OrderStage cookedStage = OrderStageTest.givenCookedStage();
//         final User receptionist = UserTest.givenReceptionist();
//         assertNotNull(cookedStage);
//         assertNotNull(receptionist);
//         assertEquals(OrderStatus.COOKED, cookedStage.getStatus());
//         assertFalse(receptionist.getRoles().contains(RoleConstant.SHIPPER));

//         assertThrows(
//             IllegalStateException.class, 
//             () -> {
//                 OrderStage.delivered(cookedStage, receptionist);
//             },
//             String.format(
//                 "OrderStage transition to %s failed because invalid role %s", 
//                 OrderStatus.DELIVERED,
//                 receptionist.getRoles()
//             )
//         );
//     }

//     @Test
//     void givenInvalidStatus_whenDelivered_thenThrowException() {
//         final OrderStage placedStage = OrderStageTest.givenPlacedStage();
//         final User shipper = UserTest.givenShipper();
//         assertNotNull(placedStage);
//         assertNotNull(shipper);
//         assertNotEquals(OrderStatus.COOKED, placedStage.getStatus());
//         assertTrue(shipper.getRoles().contains(RoleConstant.SHIPPER));

//         assertThrows(
//             IllegalStateException.class, 
//             () -> {
//                 OrderStage.delivered(placedStage, shipper);
//             },
//             String.format(
//                 "OrderStage transition to %s failed because invalid status %s",
//                 OrderStatus.DELIVERED,
//                 placedStage.getStatus()
//             )
//         );
//     }

//     public static OrderStage givenPlacedStage() {
//         final String expectedCustomerPhoneNumber = "0123456789";

//         final OrderStage placeStage = OrderStage.placed(expectedCustomerPhoneNumber);
        
//         assertNotNull(placeStage);
//         assertNotNull(placeStage.getId());
//         assertEquals(OrderConstant.CUSTOMER, placeStage.getAssigneeFirstname());
//         assertEquals(OrderConstant.CUSTOMER, placeStage.getAssigneeLastName());
//         assertEquals(OrderStatus.PLACED, placeStage.getStatus());
//         assertNotNull(placeStage.getCreatedAt());

//         return placeStage;
//     }

//     public static OrderStage givenConfirmedStage() {
//         final OrderStage placeStage = OrderStageTest.givenPlacedStage();
//         final User receptionist = UserTest.givenReceptionist();
//         assertNotNull(placeStage);
//         assertNotNull(receptionist);
//         assertEquals(OrderStatus.PLACED, placeStage.getStatus());
//         assertTrue(receptionist.getRoles().contains(RoleConstant.RECEPTIONIST));

//         final OrderStage confirmStage = OrderStage.confirmed(
//             placeStage, 
//             receptionist
//         );

//         assertNotNull(confirmStage);
//         assertEquals(placeStage.getId(), confirmStage.getId());
//         assertEquals(receptionist.getId(), UserId.fromString(confirmStage.getAssigneeId()));
//         assertEquals(receptionist.getFirstname(), confirmStage.getAssigneeFirstname());
//         assertEquals(receptionist.getLastname(), confirmStage.getAssigneeLastName());
//         assertEquals(OrderStatus.CONFIRMED, confirmStage.getStatus());
//         assertNotNull(confirmStage.getCreatedAt());

//         return confirmStage;
//     }

//     public static OrderStage givenCookedStage() {
//         final OrderStage confirmedStage = OrderStageTest.givenConfirmedStage();
//         final User chef = UserTest.givenChef();
//         assertNotNull(confirmedStage);
//         assertNotNull(chef);
//         assertEquals(OrderStatus.CONFIRMED, confirmedStage.getStatus());
//         assertTrue(chef.getRoles().contains(RoleConstant.CHEF));

//         final OrderStage cookedStage = OrderStage.cooked(
//             confirmedStage, 
//             chef
//         );

//         assertNotNull(cookedStage);
//         assertEquals(confirmedStage.getId(), cookedStage.getId());
//         assertEquals(chef.getId(), UserId.fromString(cookedStage.getAssigneeId()));
//         assertEquals(chef.getFirstname(), cookedStage.getAssigneeFirstname());
//         assertEquals(chef.getLastname(), cookedStage.getAssigneeLastName());
//         assertEquals(OrderStatus.COOKED, cookedStage.getStatus());
//         assertNotNull(cookedStage.getCreatedAt());

//         return cookedStage;
//     }

//     public static OrderStage givenDeliveredStage() {
//         final OrderStage cookedStage = OrderStageTest.givenCookedStage();
//         final User shipper = UserTest.givenShipper();
//         assertNotNull(cookedStage);
//         assertNotNull(shipper);
//         assertEquals(OrderStatus.COOKED, cookedStage.getStatus());
//         assertTrue(shipper.getRoles().contains(RoleConstant.SHIPPER));

//         final OrderStage deliveredStage = OrderStage.delivered(
//             cookedStage, 
//             shipper
//         );

//         assertNotNull(deliveredStage);
//         assertEquals(cookedStage.getId(), deliveredStage.getId());
//         assertEquals(shipper.getId(), UserId.fromString(deliveredStage.getAssigneeId()));
//         assertEquals(shipper.getFirstname(), deliveredStage.getAssigneeFirstname());
//         assertEquals(shipper.getLastname(), deliveredStage.getAssigneeLastName());
//         assertEquals(OrderStatus.DELIVERED, deliveredStage.getStatus());
//         assertNotNull(deliveredStage.getCreatedAt());

//         return deliveredStage;
//     }
// }
