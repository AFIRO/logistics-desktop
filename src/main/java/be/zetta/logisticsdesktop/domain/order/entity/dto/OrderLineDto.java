package be.zetta.logisticsdesktop.domain.order.entity.dto;

import be.zetta.logisticsdesktop.domain.product.entity.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Builder
public class OrderLineDto {
    private String lineId;
    @NotNull(message = "Order line must have a product.")
    private ProductDto product;
    @NotNull(message = "Quantity should be filled in.")
    @Positive(message = "Quantity ordered can not be zero or negative.")
    private Integer quantityOrdered;
    @NotNull(message = "Unit Price should be filled in.")
    @Positive(message = "Unit price can not be zero or negative.")
    private double unitPriceOrderLine;

    @NotNull(message = "Order line must have an order.")
    private CustomerOrderDto customerOrder;
}
