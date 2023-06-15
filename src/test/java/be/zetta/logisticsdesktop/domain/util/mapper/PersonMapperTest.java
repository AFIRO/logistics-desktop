package be.zetta.logisticsdesktop.domain.util.mapper;

import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.util.mapper.UtilTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonMapperTest {
    @Mock
    private AddressMapper addressMapper;
    @InjectMocks
    private PersonMapper personMapper;

    @Test
    void toDto() {
        Person startData = getPerson();
        PersonDto expected = getPersonDto();
        when(addressMapper.toDto(startData.getAddress())).thenReturn(expected.getAddress());
        PersonDto actual = personMapper.toDto(startData);

        assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
    }

    @Test
    void toEntity() {
        PersonDto startData = getPersonDto();
        Person expected = getPerson();
        when(addressMapper.toEntity(startData.getAddress())).thenReturn(expected.getAddress());
        Person actual = personMapper.toEntity(startData);

        assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());

    }

    @Test
    void updateEntity() {
        Person expected = getUpdatedPerson();
        Person startData = getPerson();
        PersonDto updateInfo = getUpdatedPersonDto();
        when(addressMapper.updateEntity(startData.getAddress(), updateInfo.getAddress())).thenReturn(expected.getAddress());

        Person actual = personMapper.updateEntity(startData, updateInfo);

        assertThat(actual.getFirstName()).isEqualTo(expected.getFirstName());
        assertThat(actual.getLastName()).isEqualTo(expected.getLastName());
        assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPhoneNumber()).isEqualTo(expected.getPhoneNumber());
    }
}