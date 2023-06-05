package com.weride.repository;

import com.weride.model.Order;
import com.weride.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByDriverId(Long driverId);

    List<Order> findByRiderId(Long driverId);

    List<Order> findByStatus(Status status);
}
