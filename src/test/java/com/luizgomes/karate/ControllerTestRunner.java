package com.luizgomes.karate;

import com.intuit.karate.junit5.Karate;
import com.luizgomes.ProductAppApplication;
import com.luizgomes.controller.OrderController;
import com.luizgomes.controller.OrderItemController;
import com.luizgomes.controller.ProductController;
import com.luizgomes.data.converter.OrderConverter;
import com.luizgomes.model.Order;
import com.luizgomes.model.OrderItem;
import com.luizgomes.model.Product;
import com.luizgomes.service.OrderItemService;
import com.luizgomes.service.OrderService;
import com.luizgomes.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Collections;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,classes={ProductAppApplication.class, OrderController.class, ProductController.class, OrderItemController.class,OrderConverter.class})
public class ControllerTestRunner {

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private ProductService productService;

    @BeforeEach
    public void setup() {
        // Mock for getting an order by ID
        when(orderService.getOrderById(1L)).thenReturn(createOrderById(1L));

        // Mock for getting all orders
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(createOrderById(1L), createOrderById(2L)));

        // Mock for creating a new order
        when(orderService.createOrder(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            return order;
        });

        // Mock for updating an existing order
        when(orderService.updateOrder(anyLong(),any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(1);
            return order;
        });

        // Mock for deleting an order
        when(orderService.deleteOrder(1L)).thenReturn(true);

        // Mock get all order items
        when(orderItemService.getAllOrderItems()).thenReturn(Collections.emptyList());

        // Mock get order item by ID
        when(orderItemService.getOrderItemById(anyLong())).thenAnswer(new Answer<OrderItem>() {
            @Override
            public OrderItem answer(InvocationOnMock invocation) throws Throwable {
                Long id = invocation.getArgument(0);
                return createOrderItemById(id);
            }
        });

        // Mock create order item
        when(orderItemService.createOrderItem(any(OrderItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Mock update order item
        when(orderItemService.updateOrderItem(eq(1L), any(OrderItem.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));

        // Mock delete order item
        when(orderItemService.deleteOrderItem(1L)).thenReturn(true);

        // Mock dynamic Order and Product services
        when(orderService.getOrderById(anyLong())).thenAnswer(new Answer<Order>() {
            @Override
            public Order answer(InvocationOnMock invocation) throws Throwable {
                Long id = invocation.getArgument(0);
                return createOrderById(id);
            }
        });

        when(productService.getProductById(anyLong())).thenAnswer(new Answer<Product>() {
            @Override
            public Product answer(InvocationOnMock invocation) throws Throwable {
                Long id = invocation.getArgument(0);
                return createProductById(id);
            }
        });

        // Mock get all products
        when(productService.getAllProducts()).thenReturn(Collections.emptyList());

        // Mock get product by ID
        when(productService.getProductById(1L)).thenReturn(createProductById(1L));

        // Mock create product
        when(productService.createProduct(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Mock update product
        when(productService.updateProduct(eq(1L), any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(1));

        // Mock delete product
        when(productService.deleteProduct(1L)).thenReturn(true);
    }

    private OrderItem createOrderItemById(long id) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        orderItem.setOrder(createOrderById(1L));
        orderItem.setProduct(createProductById(1L));
        orderItem.setQuantity(2);
        return orderItem;
    }

    private Order createOrderById(long id) {
        Order order = new Order();
        order.setId(id);
        return order;
    }

    private Product createProductById(long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

    @Karate.Test
    Karate testOrderController() {
        return Karate.run("OrderControllerTest","OrderItemControllerTest","ProductControllerTest").relativeTo(getClass());
    }
}
