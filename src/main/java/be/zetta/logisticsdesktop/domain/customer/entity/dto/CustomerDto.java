package be.zetta.logisticsdesktop.domain.customer.entity.dto;

import be.zetta.logisticsdesktop.domain.order.entity.dto.CustomerOrderDto;
import be.zetta.logisticsdesktop.domain.util.entity.dto.AddressDto;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CustomerDto {
    private String customerId;
    @NotBlank(message = "Customer name must be not be empty.")
    private String name;
    private byte[] logo;
    @NotBlank(message = "Customer address must be not be empty.")
    private AddressDto address;
    @NotBlank(message = "Customer phone number must be not be empty.")
    private String phoneNumber;
    @NotNull(message = "Always send a list of buyers, even if empty.")
    private List<PersonDto> buyers;
    @NotNull(message = "Always send a list of orders, even if empty.")
    private List<CustomerOrderDto> orders;
}
