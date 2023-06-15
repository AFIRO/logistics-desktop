package be.zetta.logisticsdesktop.domain.medewerker;

import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.entity.dto.MedewerkerDto;
import be.zetta.logisticsdesktop.domain.util.entity.Address;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.entity.dto.AddressDto;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;

public class MedewerkerTestData {
    public static final String TEST_ID = "TEST_ID";
    public static final String TEST_STREET = "TEST_STREET";
    public static final String TEST_HOUSE_NUMBER = "69";
    public static final String TEST_COUNTRY = "TEST_COUNTRY";
    public static final String TEST_POSTAL_CODE = "TEST_POSTAL_CODE";
    public static final String TEST_MAIL = "TEST_MAIL";
    public static final String TEST_PHONENUMBER = "TEST_PHONENUMBER";
    public static final String TEST_FIRST_NAME = "TEST_FIRST_NAME";
    public static final String TEST_LAST_NAME = "TEST_LAST_NAME";
    public static final String TEST_LAST_NAME_UPDATED = "TEST_LAST_NAME_UPDATED";
    public static final String TEST_STREET_UPDATED = "TEST_STREET_UPDATED";
    public static final String TEST_FUNCTIE = "TEST_FUNCTIE";
    public static final String TEST_FUNCTIE_UPDATED = "TEST_FUNCTIE_UPDATED";
    public static final String MEDEWERKER_NOT_FOUND = "Medewerker not found";
    public static final String MEDEWERKER_ALREADY_REGISTERED_WITH_THIS_EMAIL = "Medewerker already registered with this mail.";

    public static Medewerker getMedewerker() {
        return Medewerker.builder()
                .medewerkerId(TEST_ID)
                .person(getPerson())
                .functie(TEST_FUNCTIE)
                .build();
    }

    public static MedewerkerDto getMedewerkerDto() {
        return MedewerkerDto.builder()
                .person(getPersonDto())
                .functie(TEST_FUNCTIE)
                .build();
    }

    public static Medewerker getUpdatedMedewerker() {
        return Medewerker.builder()
                .medewerkerId(TEST_ID)
                .person(getPerson())
                .functie(TEST_FUNCTIE_UPDATED)
                .build();
    }

    public static MedewerkerDto getUpdatedMedewerkerDto() {
        return MedewerkerDto.builder()
                .person(getPersonDto())
                .functie(TEST_FUNCTIE_UPDATED)
                .build();
    }

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
}
