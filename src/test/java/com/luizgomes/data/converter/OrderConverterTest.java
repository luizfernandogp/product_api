package com.luizgomes.data.converter;

import com.luizgomes.data.request.OrderDTO;
import com.luizgomes.model.Order;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

@ExtendWith(SpringExtension.class)
class OrderConverterTest {

    private OrderConverter orderConverter;

    @BeforeEach
    public void setUp() {
        orderConverter = Mappers.getMapper(OrderConverter.class);
    }

    @Test
    public void testToOrder() {
        // Prepare test data
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderDate(LocalDateTime.now());
        orderDTO.setCustomerName("Test CustomerName");

        // Convert DTO to entity
        Order order = orderConverter.toOrder(orderDTO);

        // Verify conversion
        assertEquals(orderDTO.getCustomerName(), order.getCustomerName());
        assertEquals(orderDTO.getOrderDate(), order.getOrderDate());
    }
}