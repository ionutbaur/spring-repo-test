package com.itschool.base.service;

import com.itschool.base.model.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    List<OrderDTO> findAllOrders(Long userId);

    OrderDTO placeOrder(Long userId, OrderDTO orderDTO);
}
