package com.luizgomes.data.converter;

import com.luizgomes.data.request.ProductDTO;
import com.luizgomes.model.Product;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class ProductConverterTest {

    private ProductConverter productConverter;

    @BeforeEach
    public void setUp() {
        productConverter = Mappers.getMapper(ProductConverter.class);
    }

    @Test
    public void testToProduct() {
        // Prepare test data
        ProductDTO productDTO = new ProductDTO();
        productDTO.setDescription("Test Desc");
        productDTO.setName("Test Product");

        // Convert DTO to entity
        Product product = productConverter.toProduct(productDTO);

        // Verify conversion
        assertEquals(productDTO.getDescription(), product.getDescription());
        assertEquals(productDTO.getName(), product.getName());
    }
}