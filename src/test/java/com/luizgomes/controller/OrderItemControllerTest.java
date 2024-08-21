package com.luizgomes.controller;

import com.luizgomes.data.converter.OrderItemConverter;
import com.luizgomes.data.request.OrderItemDTO;
import com.luizgomes.model.Order;
import com.luizgomes.model.OrderItem;
import com.luizgomes.model.Product;
import com.luizgomes.service.OrderItemService;
import com.luizgomes.service.OrderService;
import com.luizgomes.service.ProductService;
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
public class OrderItemControllerTest {

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private OrderItemConverter orderItemConverter;

    @Mock
    private OrderService orderService;

    @Mock
    private ProductService productService;

    private OrderItemController orderItemController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderItemController = new OrderItemController(orderItemService, orderItemConverter, orderService, productService);
    }

    @Test
    public void testGetAllOrderItems() {
        OrderItem orderItem = new OrderItem();
        List<OrderItem> orderItems = Collections.singletonList(orderItem);
        when(orderItemService.getAllOrderItems()).thenReturn(orderItems);

        ResponseEntity<List<OrderItem>> response = orderItemController.getAllOrderItems();
        verify(orderItemService).getAllOrderItems();
        assertEquals(  response.getStatusCode(),HttpStatus.OK);
        assertEquals(  response.getBody(),orderItems);
    }

    @Test
    public void testGetOrderItemById() {
        OrderItem orderItem = new OrderItem();
        when(orderItemService.getOrderItemById(anyLong())).thenReturn(orderItem);

        ResponseEntity<OrderItem> response = orderItemController.getOrderItemById(1L);
        verify(orderItemService).getOrderItemById(anyLong());
        assertEquals(  response.getStatusCode(),HttpStatus.OK);
        assertEquals(  response.getBody(),orderItem);
    }

    @Test
    public void testCreateOrderItem() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        Order order = new Order();
        Product product = new Product();
        OrderItem orderItem = new OrderItem();

        when(orderService.getOrderById(orderItemDTO.getOrderId())).thenReturn(order);
        when(productService.getProductById(orderItemDTO.getProductId())).thenReturn(product);
        when(orderItemConverter.toOrderItem(orderItemDTO)).thenReturn(orderItem);
        when(orderItemService.createOrderItem(orderItem)).thenReturn(orderItem);

        ResponseEntity<OrderItem> response = orderItemController.createOrderItem(orderItemDTO);
        verify(orderItemService).createOrderItem(any(OrderItem.class));
        assertEquals(  response.getStatusCode(),HttpStatus.CREATED);
        assertEquals(  response.getBody(),orderItem);
    }

    @Test
    public void testUpdateOrderItem() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        OrderItem existingOrderItem = new OrderItem();
        Order order = new Order();
        Product product = new Product();
        OrderItem updatedOrderItem = new OrderItem();

        when(orderItemService.getOrderItemById(anyLong())).thenReturn(existingOrderItem);
        when(orderService.getOrderById(orderItemDTO.getOrderId())).thenReturn(order);
        when(productService.getProductById(orderItemDTO.getProductId())).thenReturn(product);
        when(orderItemConverter.toOrderItem(orderItemDTO)).thenReturn(updatedOrderItem);
        when(orderItemService.updateOrderItem(anyLong(), any(OrderItem.class))).thenReturn(updatedOrderItem);

        ResponseEntity<OrderItem> response = orderItemController.updateOrderItem(1L, orderItemDTO);
        verify(orderItemService).updateOrderItem(anyLong(), any(OrderItem.class));
        assertEquals( response.getStatusCode(),HttpStatus.OK);
        assertEquals(  response.getBody(),updatedOrderItem);
    }

    @Test
    public void testDeleteOrderItem() {
        when(orderItemService.deleteOrderItem(anyLong())).thenReturn(true);

        ResponseEntity<Void> response = orderItemController.deleteOrderItem(1L);
        verify(orderItemService).deleteOrderItem(anyLong());
        assertEquals(  response.getStatusCode(),HttpStatus.NO_CONTENT);
    }

    @Test
    public void testGetOrderItemByIdNotFound() {
        when(orderItemService.getOrderItemById(anyLong())).thenReturn(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> orderItemController.getOrderItemById(1L));
        assertEquals(  exception.getStatusCode(),HttpStatus.NOT_FOUND);
    }

    @Test
    public void testCreateOrderItemOrderNotFound() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        when(orderService.getOrderById(orderItemDTO.getOrderId())).thenReturn(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> orderItemController.createOrderItem(orderItemDTO));
        assertEquals(  exception.getStatusCode(),HttpStatus.NOT_FOUND);
        assertEquals(  exception.getReason(),"Order with ID " + orderItemDTO.getOrderId() + " not found");
    }

    @Test
    public void testCreateOrderItemProductNotFound() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        Order order = new Order();
        when(orderService.getOrderById(orderItemDTO.getOrderId())).thenReturn(order);
        when(productService.getProductById(orderItemDTO.getProductId())).thenReturn(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> orderItemController.createOrderItem(orderItemDTO));
        assertEquals(  exception.getStatusCode(),HttpStatus.NOT_FOUND);
        assertEquals(  exception.getReason(),"Product with ID " + orderItemDTO.getProductId() + " not found");
    }

    @Test
    public void testUpdateOrderItemNotFound() {
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        when(orderItemService.getOrderItemById(anyLong())).thenReturn(null);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> orderItemController.updateOrderItem(1L, orderItemDTO));
        assertEquals(  exception.getStatusCode(),HttpStatus.NOT_FOUND);
        assertEquals(  exception.getReason(),"Order item with ID 1 not found");
    }

    @Test
    public void testDeleteOrderItemNotFound() {
        when(orderItemService.deleteOrderItem(anyLong())).thenReturn(false);

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> orderItemController.deleteOrderItem(1L));
        assertEquals(  exception.getStatusCode(),HttpStatus.NOT_FOUND);
        assertEquals(  exception.getReason(),"Order item with ID 1 not found");
    }
}
