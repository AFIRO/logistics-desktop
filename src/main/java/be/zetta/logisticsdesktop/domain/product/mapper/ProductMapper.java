package be.zetta.logisticsdesktop.domain.product.mapper;

import be.zetta.logisticsdesktop.domain.product.entity.Product;
import be.zetta.logisticsdesktop.domain.product.entity.dto.ProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .numberInStock(product.getNumberInStock())
                .unitPrice(product.getUnitPrice())
                .expectedDeliveryDate(product.getExpectedDeliveryDate())
                .picture(product.getPicture())
                .build();
    }

    public Product toEntity(ProductDto product) {
        return Product.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .description(product.getDescription())
                .numberInStock(product.getNumberInStock())
                .unitPrice(product.getUnitPrice())
                .expectedDeliveryDate(product.getExpectedDeliveryDate())
                .picture(product.getPicture())
                .build();
    }

    public Product updateEntity(Product toUpdate, ProductDto updateInfo) {
        toUpdate.setDescription(updateInfo.getDescription());
        toUpdate.setName(updateInfo.getName());
        toUpdate.setNumberInStock(updateInfo.getNumberInStock());
        toUpdate.setUnitPrice(updateInfo.getUnitPrice());
        toUpdate.setExpectedDeliveryDate(updateInfo.getExpectedDeliveryDate());
        toUpdate.setPicture(updateInfo.getPicture());
        return toUpdate;
    }
}
