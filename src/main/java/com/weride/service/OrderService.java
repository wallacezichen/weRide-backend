package com.weride.service;

import com.weride.model.Order;
import com.weride.model.Status;

import java.util.List;

public interface OrderService {
    Boolean createOrder(Order order);

    Order getOrderById(Long orderId);

    Boolean cancelOrder(Long orderId);

    List<Order> getOrdersByRiderId(Long riderId);

    List<Order> getOrdersByDriverId(Long driverId);

    List<Order> getOrdersByStatus(Status status);

    void calculateTripFare(Order order);

    Boolean updateOrderStatus(Long orderId, Status status);

    Boolean markOrderAsPaid(Long orderId);
}
