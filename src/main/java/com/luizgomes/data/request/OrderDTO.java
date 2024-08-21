package com.luizgomes.data.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class OrderDTO {

    private String customerName;
    private LocalDateTime orderDate;
}
