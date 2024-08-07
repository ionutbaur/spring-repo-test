package com.itschool.base.repository;

import com.itschool.base.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByUser_Id(Long id);

    List<Order> findOrdersByDescription(String description);
}
