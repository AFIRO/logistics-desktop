package be.zetta.logisticsdesktop.domain.order.mapper;

import be.zetta.logisticsdesktop.domain.order.entity.OrderLine;
import be.zetta.logisticsdesktop.domain.order.entity.dto.OrderLineDto;
import be.zetta.logisticsdesktop.domain.product.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderLineMapper {

    private final ProductMapper productMapper;

    public OrderLineDto toDto(OrderLine orderline) {
        return OrderLineDto.builder()
                .lineId(orderline.getLineId())
                .quantityOrdered(orderline.getQuantityOrdered())
                .product(productMapper.toDto(orderline.getProduct()))
                .unitPriceOrderLine(orderline.getUnitPriceOrderLine())
                .build();
    }

    public OrderLine toEntity(OrderLineDto orderLineDto) {
        return OrderLine.builder()
                .lineId(orderLineDto.getLineId())
                .quantityOrdered(orderLineDto.getQuantityOrdered())
                .unitPriceOrderLine(orderLineDto.getUnitPriceOrderLine())
                .product(productMapper.toEntity(orderLineDto.getProduct()))
                .build();

    }

    public OrderLine updateEntity(OrderLine toUpdate, OrderLineDto updateInfo) {
        toUpdate.setQuantityOrdered(updateInfo.getQuantityOrdered());
        toUpdate.setProduct(productMapper.toEntity(updateInfo.getProduct()));
        toUpdate.setUnitPriceOrderLine(updateInfo.getUnitPriceOrderLine());
        return toUpdate;
    }

}
