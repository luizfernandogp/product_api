package com.luizgomes.data.converter;

import com.luizgomes.data.request.OrderDTO;
import com.luizgomes.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface OrderConverter {

    Order toOrder(OrderDTO product);
}
