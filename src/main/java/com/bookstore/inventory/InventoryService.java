package com.bookstore.inventory;

import com.bookstore.orders.OrderValidationService;
import org.springframework.stereotype.Service;

/**
 * Service for managing inventory within the {@code inventory} module.
 * <p>
 * This service validates orders using the public API of the {@code orders}
 * module ({@link OrderValidationService}) instead of reaching into the
 * module-private {@code orders.internal} package.
 * This is the key fix that makes the Spring Modulith verification pass.
 */
@Service
public class InventoryService {

    private final OrderValidationService orderValidationService;

    public InventoryService(OrderValidationService orderValidationService) {
        this.orderValidationService = orderValidationService;
    }

    /**
     * Reserves inventory for a given order.
     *
     * @param orderId  the order identifier
     * @param quantity the quantity to reserve
     * @return {@code true} if reservation was successful
     */
    public boolean reserveInventory(String orderId, int quantity) {
        if (!orderValidationService.isValid(orderId)) {
            throw new IllegalArgumentException("Invalid order ID: " + orderId);
        }
        // Inventory reservation logic would go here
        return true;
    }
}
