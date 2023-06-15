package be.zetta.logisticsdesktop.domain.util.mapper;

import be.zetta.logisticsdesktop.domain.util.entity.Address;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.entity.dto.AddressDto;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;

public class UtilTestData {
    public static final String TEST_ID = "TEST_ID";
    public static final String TEST_STREET = "TEST_STREET";
    public static final String TEST_HOUSE_NUMBER = "69";
    public static final String TEST_COUNTRY = "TEST_COUNTRY";
    public static final String TEST_POSTAL_CODE = "TEST_POSTAL_CODE";
    public static final String TEST_MAIL = "TEST_MAIL";
    public static final String TEST_PHONENUMBER = "TEST_PHONENUMBER";
    public static final String TEST_FIRST_NAME = "TEST_FIRST_NAME";
    public static final String TEST_LAST_NAME = "TEST_LAST_NAME";
    public static final String TEST_FIRST_NAME_NEW = "TEST_FIRST_NAME_NEW";
    public static final String TEST_LAST_NAME_NEW = "TEST_LAST_NAME_NEW";
    public static final String TEST_LAST_NAME_UPDATED = "TEST_LAST_NAME_UPDATED";
    public static final String TEST_STREET_UPDATED = "TEST_STREET_UPDATED";
    public static final String TEST_ID_NEW = "TEST_ID_NEW";

    public static Person getPerson() {
        return Person.builder()
                .personId(TEST_ID)
                .address(getAddress())
                .email(TEST_MAIL)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .phoneNumber(TEST_PHONENUMBER)
                .build();
    }

    public static PersonDto getPersonDto() {
        return PersonDto.builder()
                .personId(TEST_ID)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .phoneNumber(TEST_PHONENUMBER)
                .email(TEST_MAIL)
                .address(getAddressDto())
                .build();
    }

    public static Person getUpdatedPerson() {
        return Person.builder()
                .personId(TEST_ID)
                .email(TEST_MAIL)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME_UPDATED)
                .phoneNumber(TEST_PHONENUMBER)
                .address(getAddress())
                .build();
    }

    public static PersonDto getUpdatedPersonDto() {
        return PersonDto.builder()
                .personId(TEST_ID)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME_UPDATED)
                .phoneNumber(TEST_PHONENUMBER)
                .email(TEST_MAIL)
                .address(getAddressDto())
                .build();
    }

    public static Address getAddress() {
        return Address.builder()
                .addressId(TEST_ID)
                .street(TEST_STREET)
                .houseNumber(TEST_HOUSE_NUMBER)
                .postalCode(TEST_POSTAL_CODE)
                .country(TEST_COUNTRY)
                .build();
    }

    public static AddressDto getAddressDto() {
        return AddressDto.builder()
                .street(TEST_STREET)
                .postalCode(TEST_POSTAL_CODE)
                .country(TEST_COUNTRY)
                .houseNumber(TEST_HOUSE_NUMBER)
                .build();
    }

    public static Address getUpdatedAddress() {
        return Address.builder()
                .addressId(TEST_ID)
                .street(TEST_STREET_UPDATED)
                .houseNumber(TEST_HOUSE_NUMBER)
                .postalCode(TEST_POSTAL_CODE)
                .country(TEST_COUNTRY)
                .build();
    }

    public static AddressDto getUpdatedAddressDto() {
        return AddressDto.builder()
                .street(TEST_STREET_UPDATED)
                .postalCode(TEST_POSTAL_CODE)
                .country(TEST_COUNTRY)
                .houseNumber(TEST_HOUSE_NUMBER)
                .build();
    }

    public static Person getNewPerson() {
        return Person.builder()
                .personId(TEST_ID_NEW)
                .address(getAddress())
                .email(TEST_MAIL)
                .firstName(TEST_FIRST_NAME)
                .lastName(TEST_LAST_NAME)
                .phoneNumber(TEST_PHONENUMBER)
                .build();
    }

    public static PersonDto getNewPersonDto() {
        return PersonDto.builder()
                .address(getAddressDto())
                .email(TEST_MAIL)
                .firstName(TEST_FIRST_NAME_NEW)
                .lastName(TEST_LAST_NAME_NEW)
                .phoneNumber(TEST_PHONENUMBER)
                .build();
    }

    public static PersonDto getUnknownPersonDto() {
        return PersonDto.builder()
                .personId(TEST_ID_NEW)
                .address(getAddressDto())
                .email(TEST_MAIL)
                .firstName(TEST_FIRST_NAME_NEW)
                .lastName(TEST_LAST_NAME_NEW)
                .phoneNumber(TEST_PHONENUMBER)
                .build();
    }
}
