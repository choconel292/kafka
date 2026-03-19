package com.gabriel.pss.kafka;

import com.gabriel.pss.payload.Order;
import com.gabriel.pss.payload.Item;
import com.gabriel.pss.payload.product;
import com.gabriel.pss.repository.ProductRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryConsumer {

    private final ProductRepository productRepository;

    // Spring injects the Repository here
    public InventoryConsumer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    @KafkaListener(topics = "pss_order", groupId = "inventoryGroup")
    public void updateInventory(Order order) {
        System.out.println(">>> INVENTORY UPDATE START for Order #" + order.getId());

        for (Item item : order.getItems()) {
            // 1. "Pull" from SQL Inventory
            productRepository.findById(item.getProductId()).ifPresentOrElse(product -> {

                // 2. Calculate and set new stock
                int oldStock = product.getStockQuantity();
                int newStock = oldStock - item.getQuantity();
                product.setStockQuantity(newStock);

                // 3. Save back to SQL
                productRepository.save(product);

                // 4. Print the update
                System.out.printf("   SUCCESS: Product [%s] (ID: %d)%n", product.getName(), product.getId());
                System.out.printf("   Stock Reduced: %d -> %d%n", oldStock, newStock);

            }, () -> {
                System.out.println("   WARNING: Product ID " + item.getProductId() + " not found in inventory!");
            });
        }
        System.out.println(">>> INVENTORY UPDATE COMPLETE");
    }
}