package io.locngo.pizza.store.order.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import io.locngo.pizza.store.common.constant.OrderConstant;
import io.locngo.pizza.store.common.validation.ApiValidator;
import io.locngo.pizza.store.user.domain.Role;
import io.locngo.pizza.store.user.domain.User;
import lombok.Getter;

@Getter
public class OrderStage {

    private final OrderStageId id;

    private final String assigneeId;

    private final String assigneeFirstname;

    private final String assigneeLastName;

    private final OrderStatus status;

    private final LocalDateTime createdAt;

    private OrderStage(
        final OrderStageId id,
        final String assigneeId,
        final String assigneeFirstname,
        final String assigneeLastname,
        final OrderStatus status,
        final LocalDateTime createdAt
    ) {
        ApiValidator.requireNonNull(id, "id");
        ApiValidator.requireStringNonBlank(assigneeId, "assigneeId");
        ApiValidator.requireStringNonBlank(assigneeFirstname, "assigneeFirstname");
        ApiValidator.requireStringNonBlank(assigneeFirstname, "assigneeLastname");
        ApiValidator.requireNonNull(status, "status");
        ApiValidator.requireNonNull(createdAt, "createdAt");

        this.id = id;
        this.assigneeId = assigneeId;
        this.assigneeFirstname = assigneeFirstname;
        this.assigneeLastName = assigneeLastname;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static OrderStage of(
        final OrderStageId id,
        final String assigneeId,
        final String assigneeFirstname,
        final String assigneeLastname,
        final OrderStatus status,
        final LocalDateTime createdAt
    ) {
        return new OrderStage(id, assigneeId, assigneeFirstname, assigneeLastname, status, createdAt);
    }

    public static OrderStage placed(final String customerPhoneNumber) {
        return new OrderStage(
            OrderStageId.newInstance(), 
            customerPhoneNumber, 
            OrderConstant.CUSTOMER, 
            OrderConstant.CUSTOMER, 
            OrderStatus.PLACED, 
            LocalDateTime.now()
        );
    }

    public static OrderStage confirmed(
        final OrderStage placedStage,
        final User assignee
    ) {
        ApiValidator.requireNonNull(placedStage, "placedStage");

        if(Role.ROLE_RECEPTIONIST != assignee.getRole()) {
            throw new IllegalStateException(
                String.format(
                    "OrderStage transition to %s failed because invalid role %s", 
                    OrderStatus.CONFIRMED,
                    assignee.getRole()
                )
            );
        }

        if(OrderStatus.PLACED != placedStage.getStatus()) {
            throw new IllegalStateException(
                String.format(
                    "OrderStage transition to %s failed because invalid status %s",
                    OrderStatus.CONFIRMED,
                    placedStage.getStatus()
                )
            );
        }

        return new OrderStage(
            placedStage.getId(), 
            assignee.getId().getValue().toString(),
            assignee.getFirstname(),
            assignee.getLastname(), 
            OrderStatus.CONFIRMED, 
            LocalDateTime.now()
        );
    }

    public static OrderStage cooked(
        final OrderStage confirmedStage,
        final User assignee
    ) {
        ApiValidator.requireNonNull(confirmedStage, "confirmedStage");

        if(Role.ROLE_CHEF != assignee.getRole()) {
            throw new IllegalStateException(
                String.format(
                    "OrderStage transition to %s failed because invalid role %s", 
                    OrderStatus.COOKED,
                    assignee.getRole()
                )
            );
        }

        if(OrderStatus.CONFIRMED != confirmedStage.getStatus()) {
            throw new IllegalStateException(
                String.format(
                    "OrderStage transition to %s failed because invalid status %s",
                    OrderStatus.COOKED,
                    confirmedStage.getStatus()
                )
            );
        }

        return new OrderStage(
            confirmedStage.getId(), 
            assignee.getId().getValue().toString(),
            assignee.getFirstname(),
            assignee.getLastname(), 
            OrderStatus.COOKED, 
            LocalDateTime.now()
        );
    }

    public static OrderStage delivered(
        final OrderStage cookedStage,
        final User assignee
    ) {
        ApiValidator.requireNonNull(cookedStage, "cookedStage");

        if(Role.ROLE_SHIPPER != assignee.getRole()) {
            throw new IllegalStateException(
                String.format(
                    "OrderStage transition to %s failed because invalid role %s", 
                    OrderStatus.DELIVERED,
                    assignee.getRole()
                )
            );
        }

        if(OrderStatus.COOKED != cookedStage.getStatus()) {
            throw new IllegalStateException(
                String.format(
                    "OrderStage transition to %s failed because invalid status %s",
                    OrderStatus.DELIVERED,
                    cookedStage.getStatus()
                )
            );
        }

        return new OrderStage(
            cookedStage.getId(), 
            assignee.getId().getValue().toString(),
            assignee.getFirstname(),
            assignee.getLastname(), 
            OrderStatus.DELIVERED, 
            LocalDateTime.now()
        );
    }

    @Getter
    public static class OrderStageId {

        private final UUID value;

        private OrderStageId(final UUID value) {
            this.value = value;
        }

        public static OrderStageId newInstance() {
            return new OrderStageId(UUID.randomUUID());
        }

        public static OrderStageId fromString(final String value) {
            return new OrderStageId(UUID.fromString(value));
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
            OrderStageId other = (OrderStageId) obj;
            if (value == null) {
                if (other.value != null)
                    return false;
            } else if (!value.equals(other.value))
                return false;
            return true;
        }
    }
}
