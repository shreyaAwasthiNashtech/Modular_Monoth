package com.bookstore.orders.internal;

import org.springframework.stereotype.Component;

/**
 * Internal implementation of order validation logic.
 * <p>
 * This class is intentionally placed in the {@code internal} sub-package so that
 * Spring Modulith treats it as module-private. No class outside the
 * {@code com.bookstore.orders} module should import this class directly.
 * External modules must use {@link com.bookstore.orders.OrderValidationService}
 * instead.
 */
@Component
public class OrderValidator {

    public boolean isValid(String orderId) {
        return orderId != null && !orderId.isBlank();
    }
}
