package com.gabriel.pss.repository;

import com.gabriel.pss.payload.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}