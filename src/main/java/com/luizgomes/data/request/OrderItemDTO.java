package com.luizgomes.data.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderItemDTO {

    private Long orderId;
    private Long productId;
    private int quantity;
}
