package com.itschool.base.service.impl;

import com.itschool.base.entity.Order;
import com.itschool.base.entity.User;
import com.itschool.base.model.OrderDTO;
import com.itschool.base.repository.OrderRepository;
import com.itschool.base.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    private static final Long USER_ID = 12345L;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private User user;

    private OrderServiceImpl orderServiceImpl;

    @BeforeEach
    void setUp() {
        orderServiceImpl = new OrderServiceImpl(orderRepository, userRepository);
    }

    @Test
    void findAllOrders() {
        // we define the behavior of the mock object
        // when the method findByUserId is called with the USER_ID as an argument, it should return our list of orders (createOrderList()) instead of querying the database
        Mockito.when(orderRepository.findByUserId(USER_ID))
                .thenReturn(createOrderList());

        // we call the method we want to test
        List<OrderDTO> orderDTOList = orderServiceImpl.findAllOrders(USER_ID);

        // we verify that the method findByUserId was called with the USER_ID as an argument
        Mockito.verify(orderRepository).findByUserId(USER_ID);

        // we verify that the method findAllOrders returns a list of OrderDTO objects with the same size of the list we created (createOrderList())
        Assertions.assertEquals(2, orderDTOList.size());

        // we verify that the OrderDTO objects have the same id and description as the Order objects in the list we created (createOrderList())
        OrderDTO orderDTO1 = orderDTOList.getFirst();
        Assertions.assertEquals(12L, orderDTO1.id());
        Assertions.assertEquals("Order 1", orderDTO1.description());

        OrderDTO orderDTO2 = orderDTOList.get(1);
        Assertions.assertEquals(34L, orderDTO2.id());
        Assertions.assertEquals("Order 2", orderDTO2.description());
    }

    @Test
    void placeOrder() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.of(user));

        Order createdOrder = createOrder();
        Mockito.when(orderRepository.save(Mockito.any(Order.class))).thenReturn(createdOrder); // we define the behavior of the mock object when the save method is called with any Order object as an argument, it should return an Order object (createOrder()

        OrderDTO orderToPlace = createOrderDTO();

        // we call the method we want to test
        OrderDTO orderDTO = orderServiceImpl.placeOrder(USER_ID, orderToPlace);

        // we check that our returned orderDTO has the same id and description as the createdOrder
        Assertions.assertEquals(createdOrder.getId(), orderDTO.id());
        Assertions.assertEquals(createdOrder.getDescription(), orderDTO.description());
    }

    @Test
    void placeOrder_userDoesNotExist() {
        Mockito.when(userRepository.findById(USER_ID)).thenReturn(Optional.empty());
        ResponseStatusException exception = Assertions.assertThrows(ResponseStatusException.class,
                () -> orderServiceImpl.placeOrder(USER_ID, null)); // we expect a ResponseStatusException to be thrown when calling orderServiceImpl.placeOrder if the user does not exist

        Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        Assertions.assertEquals("User with id: 12345 not found", exception.getReason());
    }

    private OrderDTO createOrderDTO() {
        return new OrderDTO(null, "fake Order description");
    }

    private Order createOrder() {
        Order order = new Order("another fake Order description", user);
        order.setId(1L);

        return order;
    }

    private List<Order> createOrderList() {
        Order order1 = new Order("Order 1");
        order1.setId(12L);

        Order order2 = new Order("Order 2");
        order2.setId(34L);

        return List.of(order1, order2);
    }
}