package com.itschool.base.controller;

import com.itschool.base.model.OrderDTO;
import com.itschool.base.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Order Manager", description = "Order Manager API that manipulates operations related to orders") // Swagger annotation to group the API endpoints under the "Order Manager" tag
@RestController
@RequestMapping("orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Place an order", description = "Place an order for a specific user and return the created order")
    @PostMapping("{userId}")
    public OrderDTO placeOrder(@PathVariable Long userId, OrderDTO orderDTO) {
        return orderService.placeOrder(userId, orderDTO);
    }

    @Operation(summary = "Find all orders for a user", description = "Find all orders for a specific user and return a list of orders")
    @GetMapping("{userId}")
    public List<OrderDTO> getAllOrders(@PathVariable Long userId) {
        return orderService.findAllOrders(userId);
    }
}
