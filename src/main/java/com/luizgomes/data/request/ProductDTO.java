package com.luizgomes.data.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductDTO {

    private String name;
    private String description;
    private BigDecimal price;
}
