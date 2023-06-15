package be.zetta.logisticsdesktop.domain.util.mapper;

import be.zetta.logisticsdesktop.domain.util.entity.Address;
import be.zetta.logisticsdesktop.domain.util.entity.dto.AddressDto;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDto toDto(Address address) {
        return AddressDto.builder()
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }

    public Address toEntity(AddressDto dto) {
        return Address.builder()
                .street(dto.getStreet())
                .houseNumber(dto.getHouseNumber())
                .country(dto.getCountry())
                .postalCode(dto.getPostalCode())
                .build();
    }

    public Address updateEntity(Address toUpdate, AddressDto updateInfo) {
        toUpdate.setStreet(updateInfo.getStreet());
        toUpdate.setHouseNumber(updateInfo.getHouseNumber());
        toUpdate.setCountry(updateInfo.getCountry());
        return toUpdate;
    }
}
