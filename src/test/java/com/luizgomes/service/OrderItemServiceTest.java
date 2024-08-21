package com.luizgomes.service;

import com.luizgomes.model.OrderItem;
import com.luizgomes.repository.OrderItemRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    private OrderItemService orderItemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderItemService = new OrderItemService(orderItemRepository);
    }

    @Test
    public void testGetAllOrderItems() {
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.findAll()).thenReturn(Collections.singletonList(orderItem));

        List<OrderItem> response = orderItemService.getAllOrderItems();
        verify(orderItemRepository).findAll();
        assertEquals(1, response.size());
        assertEquals(orderItem, response.get(0));
    }

    @Test
    public void testGetOrderItemById() {
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(orderItem));

        OrderItem response = orderItemService.getOrderItemById(1L);
        verify(orderItemRepository).findById(anyLong());
        assertEquals(orderItem, response);
    }

    @Test
    public void testGetOrderItemByIdNotFound() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        OrderItem response = orderItemService.getOrderItemById(1L);
        verify(orderItemRepository).findById(anyLong());
        assertNull(response);
    }

    @Test
    public void testCreateOrderItem() {
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.save(orderItem)).thenReturn(orderItem);

        OrderItem response = orderItemService.createOrderItem(orderItem);
        verify(orderItemRepository).save(orderItem);
        assertEquals(orderItem, response);
    }

    @Test
    public void testUpdateOrderItem() {
        OrderItem existingOrderItem = new OrderItem();
        OrderItem updatedOrderItem = new OrderItem();
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(existingOrderItem));
        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(updatedOrderItem);

        OrderItem response = orderItemService.updateOrderItem(1L, updatedOrderItem);
        verify(orderItemRepository).findById(anyLong());
        assertEquals(updatedOrderItem, response);
    }

    @Test
    public void testUpdateOrderItemNotFound() {
        OrderItem orderItem = new OrderItem();
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        OrderItem response = orderItemService.updateOrderItem(1L, orderItem);
        verify(orderItemRepository).findById(anyLong());
        verify(orderItemRepository, times(0)).save(any(OrderItem.class));
        assertNull(response);
    }

    @Test
    public void testDeleteOrderItem() {
        when(orderItemRepository.existsById(anyLong())).thenReturn(true);

        boolean result = orderItemService.deleteOrderItem(1L);
        verify(orderItemRepository).deleteById(anyLong());
        assertTrue(result);
        verify(orderItemRepository).existsById(anyLong());
    }

    @Test
    public void testDeleteOrderItemNotFound() {
        when(orderItemRepository.existsById(anyLong())).thenReturn(false);

        boolean result = orderItemService.deleteOrderItem(1L);
        verify(orderItemRepository, never()).deleteById(anyLong());
        assertFalse(result);
        verify(orderItemRepository).existsById(anyLong());
    }
}