package com.luizgomes.controller;

import com.luizgomes.data.converter.OrderItemConverter;
import com.luizgomes.data.request.OrderItemDTO;
import com.luizgomes.model.Order;
import com.luizgomes.model.OrderItem;
import com.luizgomes.model.Product;
import com.luizgomes.service.OrderItemService;
import com.luizgomes.service.OrderService;
import com.luizgomes.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@Tag(name = "Order Item API", description = "API for managing order items")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemConverter orderItemConverter;
    private final OrderService orderService;
    private final ProductService productService;

    public OrderItemController(OrderItemService orderItemService, OrderItemConverter orderItemConverter, OrderService orderService, ProductService productService) {
        this.orderItemService = orderItemService;
        this.orderItemConverter = orderItemConverter;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Get all order items", description = "Retrieve a list of all order items")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of order items"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
    })
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemService.getAllOrderItems();
        return ResponseEntity.ok(orderItems);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order item by ID", description = "Retrieve an order item by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the order item"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Order item not found"),
    })
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        if (orderItem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order item with ID " + id + " not found");
        }
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping
    @Operation(summary = "Create a new order item", description = "Create a new order item")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Order or Product not found"),
    })
    public ResponseEntity<OrderItem> createOrderItem(@RequestBody OrderItemDTO orderItemDto) {
        Order order = orderService.getOrderById(orderItemDto.getOrderId());
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with ID " + orderItemDto.getOrderId() + " not found");
        }
        Product product = productService.getProductById(orderItemDto.getProductId());
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + orderItemDto.getProductId() + " not found");
        }

        OrderItem orderItem = orderItemConverter.toOrderItem(orderItemDto);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing order item", description = "Update an existing order item by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order item updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Order item, Order, or Product not found"),
    })
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long id, @RequestBody OrderItemDTO orderItemDto) {
        OrderItem existingOrderItem = orderItemService.getOrderItemById(id);
        if (existingOrderItem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order item with ID " + id + " not found");
        }

        Order order = orderService.getOrderById(orderItemDto.getOrderId());
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with ID " + orderItemDto.getOrderId() + " not found");
        }
        Product product = productService.getProductById(orderItemDto.getProductId());
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with ID " + orderItemDto.getProductId() + " not found");
        }

        OrderItem updatedOrderItem = orderItemConverter.toOrderItem(orderItemDto);
        updatedOrderItem.setOrder(order);
        updatedOrderItem.setProduct(product);
        OrderItem result = orderItemService.updateOrderItem(id, updatedOrderItem);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an order item by ID", description = "Delete an order item by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Order item deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Order item not found"),
    })
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        boolean isDeleted = orderItemService.deleteOrderItem(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order item with ID " + id + " not found");
        }
    }
}
