package be.zetta.logisticsdesktop.domain.util.mapper;

import be.zetta.logisticsdesktop.domain.util.entity.Address;
import be.zetta.logisticsdesktop.domain.util.entity.dto.AddressDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static be.zetta.logisticsdesktop.domain.util.mapper.UtilTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressMapperTest {
    private AddressMapper addressMapper;

    @BeforeAll
    private void setup() {
        addressMapper = new AddressMapper();
    }

    @Test
    void toDto() {
        AddressDto expected = getAddressDto();
        AddressDto actual = addressMapper.toDto(getAddress());

        assertThat(actual.getCountry()).isEqualTo(expected.getCountry());
        assertThat(actual.getHouseNumber()).isEqualTo(expected.getHouseNumber());
        assertThat(actual.getStreet()).isEqualTo(expected.getStreet());
        assertThat(actual.getPostalCode()).isEqualTo(expected.getPostalCode());

    }

    @Test
    void toEntity() {
        Address expected = getAddress();
        Address actual = addressMapper.toEntity(getAddressDto());

        assertThat(actual.getCountry()).isEqualTo(expected.getCountry());
        assertThat(actual.getHouseNumber()).isEqualTo(expected.getHouseNumber());
        assertThat(actual.getStreet()).isEqualTo(expected.getStreet());
        assertThat(actual.getPostalCode()).isEqualTo(expected.getPostalCode());
    }

    @Test
    void updateEntity() {
        Address expected = getUpdatedAddress();
        Address actual = addressMapper.updateEntity(getAddress(), getUpdatedAddressDto());

        assertThat(actual.getCountry()).isEqualTo(expected.getCountry());
        assertThat(actual.getHouseNumber()).isEqualTo(expected.getHouseNumber());
        assertThat(actual.getStreet()).isEqualTo(expected.getStreet());
        assertThat(actual.getPostalCode()).isEqualTo(expected.getPostalCode());
    }
}