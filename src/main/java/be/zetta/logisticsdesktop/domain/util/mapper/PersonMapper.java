package be.zetta.logisticsdesktop.domain.util.mapper;

import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonMapper {
    private final AddressMapper addressMapper;

    public PersonDto toDto(Person person) {
        return PersonDto.builder()
                .personId(person.getPersonId())
                .email(person.getEmail())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .phoneNumber(person.getPhoneNumber())
                .address(person.getAddress() != null ? addressMapper.toDto(person.getAddress()): null)
                .build();

    }

    public Person toEntity(PersonDto dto) {
        return Person.builder()
                .address(dto.getAddress() != null ? addressMapper.toEntity(dto.getAddress()): null)
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .build();
    }

    public Person updateEntity(Person toUpdate, PersonDto updateInfo) {
        toUpdate.setPhoneNumber(updateInfo.getPhoneNumber());
        toUpdate.setFirstName(updateInfo.getFirstName());
        toUpdate.setLastName(updateInfo.getLastName());
        toUpdate.setEmail(updateInfo.getEmail());
        toUpdate.setAddress(addressMapper.updateEntity(toUpdate.getAddress(), updateInfo.getAddress()));
        return toUpdate;
    }
}
