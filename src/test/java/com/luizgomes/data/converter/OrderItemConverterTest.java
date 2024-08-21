package com.luizgomes.data.converter;

import com.luizgomes.data.request.OrderItemDTO;
import com.luizgomes.model.OrderItem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class OrderItemConverterTest {

    private OrderItemConverter orderItemConverter;

    @BeforeEach
    public void setUp() {
        orderItemConverter = Mappers.getMapper(OrderItemConverter.class);
    }

    @Test
    public void testToOrderItem() {
        // Prepare test data
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        orderItemDTO.setOrderId(1L);
        orderItemDTO.setProductId(2L);

        // Convert DTO to entity
        OrderItem orderItem = orderItemConverter.toOrderItem(orderItemDTO);

        // Verify conversion
        assertEquals(orderItemDTO.getOrderId(), orderItem.getOrder().getId());
        assertEquals(orderItemDTO.getProductId(), orderItem.getProduct().getId());
    }
}