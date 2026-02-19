package com.bookstore.orders;

import com.bookstore.orders.internal.OrderValidator;
import org.springframework.stereotype.Service;

/**
 * Public facade for order-validation exposed by the {@code orders} module.
 * <p>
 * Other modules (e.g. {@code inventory}) must depend on this class rather than
 * on {@link com.bookstore.orders.internal.OrderValidator}, which is
 * module-private by Spring Modulith convention.
 */
@Service
public class OrderValidationService {

    private final OrderValidator orderValidator;

    public OrderValidationService(OrderValidator orderValidator) {
        this.orderValidator = orderValidator;
    }

    /**
     * Returns {@code true} when the given order ID is considered valid.
     *
     * @param orderId the order identifier to validate
     * @return {@code true} if valid, {@code false} otherwise
     */
    public boolean isValid(String orderId) {
        return orderValidator.isValid(orderId);
    }
}
