package com.luizgomes.service;

import static org.junit.jupiter.api.Assertions.*;

import com.luizgomes.model.Order;
import com.luizgomes.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderService = new OrderService(orderRepository);
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order();
        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        List<Order> response = orderService.getAllOrders();
        verify(orderRepository).findAll();
        assertEquals(1, response.size());
        assertEquals(order, response.get(0));
    }

    @Test
    public void testGetOrderById() {
        Order order = new Order();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order response = orderService.getOrderById(1L);
        verify(orderRepository).findById(anyLong());
        assertEquals(order, response);
    }

    @Test
    public void testGetOrderByIdNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Order response = orderService.getOrderById(1L);
        verify(orderRepository).findById(anyLong());
        assertNull(response);
    }

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        when(orderRepository.save(order)).thenReturn(order);

        Order response = orderService.createOrder(order);
        verify(orderRepository).save(order);
        assertEquals(order, response);
    }

    @Test
    public void testUpdateOrder() {
        Order existingOrder = new Order();
        Order updatedOrder = new Order();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(updatedOrder);

        Order response = orderService.updateOrder(1L, updatedOrder);
        verify(orderRepository).findById(anyLong());
        assertEquals(updatedOrder, response);
    }

    @Test
    public void testUpdateOrderNotFound() {
        Order order = new Order();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Order response = orderService.updateOrder(1L, order);
        verify(orderRepository).findById(anyLong());
        verify(orderRepository, never()).save(any(Order.class));
        assertNull(response);
    }

    @Test
    public void testDeleteOrder() {
        when(orderRepository.existsById(anyLong())).thenReturn(true);

        boolean result = orderService.deleteOrder(1L);
        verify(orderRepository).deleteById(anyLong());
        assertTrue(result);
    }

    @Test
    public void testDeleteOrderNotFound() {
        when(orderRepository.existsById(anyLong())).thenReturn(false);

        boolean result = orderService.deleteOrder(1L);
        verify(orderRepository, never()).deleteById(anyLong());
        assertFalse(result);
    }

    @Test
    public void testUpdateOrderWithInvalidId() {
        Order orderToUpdate = new Order();
        orderToUpdate.setCustomerName("Updated Customer");
        orderToUpdate.setOrderDate(null); // assuming the order date is not updated

        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        Order result = orderService.updateOrder(1L, orderToUpdate);

        verify(orderRepository).findById(anyLong());
        verify(orderRepository, never()).save(any(Order.class));
        assertNull(result, "Expected result to be null when the order with the provided ID is not found.");
    }
}