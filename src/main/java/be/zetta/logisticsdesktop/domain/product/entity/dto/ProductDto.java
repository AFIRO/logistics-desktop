package be.zetta.logisticsdesktop.domain.product.entity.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@Builder
public class ProductDto {
    private String productId;
    @NotNull(message = "A product requires a name.")
    @NotBlank(message = "A product requires a name.")
    private String name;
    @NotNull(message = "A product requires a price.")
    @Positive(message = "A product requires a positive price.")
    private double unitPrice;
    @NotNull(message = "A product requires a description.")
    @NotBlank(message = "A product requires a description.")
    private String description;
    @NotNull(message = "A product requires a stock.")
    @Positive(message = "A product requires a positive stock.")
    private int numberInStock;
    @NotNull(message = "A product requires an Expected Delivery Date.")
    private LocalDate expectedDeliveryDate;
    private byte[] picture;
}
