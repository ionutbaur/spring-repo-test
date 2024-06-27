package com.itschool.base.service.impl;

import com.itschool.base.entity.Order;
import com.itschool.base.entity.User;
import com.itschool.base.model.OrderDTO;
import com.itschool.base.repository.OrderRepository;
import com.itschool.base.repository.UserRepository;
import com.itschool.base.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<OrderDTO> findAllOrders(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> new OrderDTO(order.getId(), order.getDescription()))
                .toList();
    }

    @Override
    public OrderDTO placeOrder(Long userId, OrderDTO orderDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id: " + userId + " not found"));
        Order order = new Order(orderDTO.description(), user);
        Order createdOrder = orderRepository.save(order);

        return new OrderDTO(createdOrder.getId(), createdOrder.getDescription());
    }
}
