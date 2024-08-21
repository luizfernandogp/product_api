package com.luizgomes.data.converter;

import com.luizgomes.data.request.ProductDTO;
import com.luizgomes.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface ProductConverter {

    Product toProduct(ProductDTO product);
}
