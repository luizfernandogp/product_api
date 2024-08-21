package com.luizgomes.controller;

import com.luizgomes.data.converter.OrderConverter;
import com.luizgomes.data.request.OrderDTO;
import com.luizgomes.model.Order;
import com.luizgomes.service.OrderService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderConverter orderConverter;

    private OrderController orderController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderController = new OrderController(orderService, orderConverter);
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order();
        List<Order> orders = Collections.singletonList(order);
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<Order>> response = orderController.getAllOrders();
        verify(orderService).getAllOrders();
        assertEquals(  response.getStatusCode(),HttpStatus.OK);
        assertEquals(  response.getBody(),orders);
    }

    @Test
    public void testGetOrderById() {
        Order order = new Order();
        when(orderService.getOrderById(anyLong())).thenReturn(order);

        ResponseEntity<Order> response = orderController.getOrderById(1L);
        verify(orderService).getOrderById(anyLong());
        assertEquals(  response.getStatusCode(),HttpStatus.OK);
        assertEquals(  response.getBody(),order);
    }

    @Test
    public void testCreateOrder() {
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();
        when(orderConverter.toOrder(orderDTO)).thenReturn(order);
        when(orderService.createOrder(order)).thenReturn(order);

        ResponseEntity<Order> response = orderController.createOrder(orderDTO);
        verify(orderService).createOrder(any(Order.class));
        assertEquals(  response.getStatusCode(),HttpStatus.CREATED);
        assertEquals(  response.getBody(),order);
    }

    @Test
    public void testUpdateOrder() {
        OrderDTO orderDTO = new OrderDTO();
        Order order = new Order();
        when(orderConverter.toOrder(orderDTO)).thenReturn(order);
        when(orderService.updateOrder(anyLong(), any(Order.class))).thenReturn(order);

        ResponseEntity<Order> response = orderController.updateOrder(1L, orderDTO);
        verify(orderService).updateOrder(anyLong(), any(Order.class));
        assertEquals( response.getStatusCode(),HttpStatus.OK);
        assertEquals( response.getBody(),order);
    }

    @Test
    public void testDeleteOrder() {
        when(orderService.deleteOrder(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = orderController.deleteOrder(1L);
        verify(orderService).deleteOrder(anyLong());
        assertEquals(  response.getStatusCode(),HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetOrderByIdNotFound() {
        when(orderService.getOrderById(anyLong())).thenReturn(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> orderController.getOrderById(1L));
        assertEquals(  exception.getStatusCode(),HttpStatus.NOT_FOUND);
    }
}
