package com.weride.service.impl;

import com.weride.model.Order;
import com.weride.model.Status;
import com.weride.model.UserCardRelation;
import com.weride.repository.OrderRepository;
import com.weride.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    private boolean OrderExists(Order order) {
        // Implement logic to check if the card exists
        for(Order curOrder : orderRepository.findAll()) {
            if(Objects.equals(order.getId(), curOrder.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean createOrder(Order order) {
        // Implement the logic to create and save the order
        // Set default values if needed
        // You can call other methods or perform additional operations here
        if (OrderExists(order)) {
            return false;
        }
        orderRepository.save(order);
        return true;
    }

    @Override
    @Transactional
    public Order getOrderById(Long orderId) {
        // Implement the logic to retrieve an order by its ID
        // You can call other methods or perform additional operations here
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    @Transactional
    public Boolean cancelOrder(Long orderId) {
        // Implement the logic to cancel an order
        // You can call other methods or perform additional operations here
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(Status.CANCELED);
            return true;
        }
        return false;
    }

    @Override
    public List<Order> getOrdersByRiderId(Long riderId) {
        // Implement the logic to retrieve orders associated with a specific rider
        // You can call other methods or perform additional operations here
        return orderRepository.findByRiderId(riderId);
    }

    @Override
    public List<Order> getOrdersByDriverId(Long driverId) {
        // Implement the logic to retrieve orders associated with a specific driver
        // You can call other methods or perform additional operations here
        return orderRepository.findByDriverId(driverId);
    }

    @Override
    public List<Order> getOrdersByStatus(Status status) {
        // Implement the logic to retrieve orders with a specific status
        // You can call other methods or perform additional operations here
        return orderRepository.findByStatus(status);
    }

    @Override
    public void calculateTripFare(Order order) {
        // Implement the logic to calculate the trip fare for an order
        // You can call other methods or perform additional operations here
        // Update the trip fare value in the order object
        //todo
    }

    @Override
    public Boolean updateOrderStatus(Long orderId, Status status) {
        // Implement the logic to update the status of an order
        // You can call other methods or perform additional operations here
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
            return true;
        }
        return false;
    }

    @Override
    public Boolean markOrderAsPaid(Long orderId) {
        // Implement the logic to mark an order as paid
        // You can call other methods or perform additional operations here
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setIsPaid(true);
            return true;
        }
        return false;
    }


}
