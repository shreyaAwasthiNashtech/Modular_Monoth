package com.bookstore.orders;

import org.springframework.stereotype.Service;

/**
 * Primary service of the {@code orders} module.
 * Handles business logic related to order management.
 */
@Service
public class OrderService {

    private final OrderValidationService orderValidationService;

    public OrderService(OrderValidationService orderValidationService) {
        this.orderValidationService = orderValidationService;
    }

    /**
     * Places an order after validating the given order ID.
     *
     * @param orderId the identifier for the new order
     * @return {@code true} if the order was placed successfully
     */
    public boolean placeOrder(String orderId) {
        if (!orderValidationService.isValid(orderId)) {
            throw new IllegalArgumentException("Invalid order ID: " + orderId);
        }
        // Business logic for placing the order would go here
        return true;
    }
}
