package com.weride.controller;

import com.weride.model.Order;
import com.weride.model.Status;
import com.weride.repository.OrderRepository;
import com.weride.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-service")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        boolean created = orderService.createOrder(order);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create order");
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable("orderId") Long orderId) {
        boolean canceled = orderService.cancelOrder(orderId);
        if (canceled) {
            return ResponseEntity.ok("Order canceled successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to cancel order");
        }
    }

    @GetMapping("/rider/{riderId}")
    public ResponseEntity<List<Order>> getOrdersByRiderId(@PathVariable("riderId") Long riderId) {
        List<Order> orders = orderService.getOrdersByRiderId(riderId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Order>> getOrdersByDriverId(@PathVariable("driverId") Long driverId) {
        List<Order> orders = orderService.getOrdersByDriverId(driverId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable("status") Status status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestBody Status status) {
        boolean updated = orderService.updateOrderStatus(orderId, status);
        if (updated) {
            return ResponseEntity.ok("Order status updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update order status");
        }
    }

    @PutMapping("/{orderId}/mark-paid")
    public ResponseEntity<String> markOrderAsPaid(@PathVariable("orderId") Long orderId) {
        boolean markedAsPaid = orderService.markOrderAsPaid(orderId);
        if (markedAsPaid) {
            return ResponseEntity.ok("Order marked as paid successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to mark order as paid");
        }
    }
}
