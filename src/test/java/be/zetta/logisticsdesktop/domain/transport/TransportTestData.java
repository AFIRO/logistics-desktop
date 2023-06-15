package be.zetta.logisticsdesktop.domain.transport;

import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceExtraVerification;
import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceTemplate;
import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TrackAndTraceTemplateDto;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;


import java.util.ArrayList;
import java.util.Arrays;


import static be.zetta.logisticsdesktop.domain.util.mapper.UtilTestData.*;
import static be.zetta.logisticsdesktop.domain.util.mapper.UtilTestData.getNewPerson;

public class TransportTestData {

    public static final String TEST_ID = "TEST_ID";
    public static final boolean TEST_ONLY_NUMBERS = true;
    public static final int TEST_NUMBER_OF_CHARS = 7;
    public static final TrackAndTraceExtraVerification TEST_EXTRA_VERIFICATION = TrackAndTraceExtraVerification.POSTALCODE;
    public static final String TEST_PREFIX = "TEST_PREFIX";
    public static final String TEST_PREFIX_UPDATED = "TEST_PREFIX_UPDATED";
    public static final String TEST_TRANSPORT_NAME = "TEST_TRANSPORT_NAME";
    public static final boolean TEST_ACTIVE = true;
    public static final String TEST_TRANSPORT_NAME_UPDATED = "TEST_TRANSPORT_NAME_UPDATED";
    public static final String ALREADY_REGISTERED = "Transport already registered with this name.";
    public static final String UNKNOWN_CONTACT_IN_DTO = "PersonId in transport update dto does not match known contacts.";
    public static final String NOT_FOUND = "Transport not found";

    public static TrackAndTraceTemplate getTrackAndTraceTemplate(){
        return TrackAndTraceTemplate.builder()
                .trackAndTraceTemplateId(TEST_ID)
                .onlyNumbers(TEST_ONLY_NUMBERS)
                .numberOfChars(TEST_NUMBER_OF_CHARS)
                .extraVerification(TEST_EXTRA_VERIFICATION)
                .prefix(TEST_PREFIX)
                .build();
    }

    public static TrackAndTraceTemplateDto getTrackAndTraceTemplateDto(){
        return TrackAndTraceTemplateDto.builder()
                .trackAndTraceTemplateId(TEST_ID)
                .onlyNumbers(TEST_ONLY_NUMBERS)
                .numberOfChars(TEST_NUMBER_OF_CHARS)
                .extraVerification(TEST_EXTRA_VERIFICATION)
                .prefix(TEST_PREFIX)
                .build();
    }

    public static TrackAndTraceTemplate getTrackAndTraceTemplateUpdated(){
        return TrackAndTraceTemplate.builder()
                .trackAndTraceTemplateId(TEST_ID)
                .onlyNumbers(TEST_ONLY_NUMBERS)
                .numberOfChars(TEST_NUMBER_OF_CHARS)
                .extraVerification(TEST_EXTRA_VERIFICATION)
                .prefix(TEST_PREFIX_UPDATED)
                .build();
    }

    public static TrackAndTraceTemplateDto getTrackAndTraceTemplateDtoUpdated(){
        return TrackAndTraceTemplateDto.builder()
                .trackAndTraceTemplateId(TEST_ID)
                .onlyNumbers(TEST_ONLY_NUMBERS)
                .numberOfChars(TEST_NUMBER_OF_CHARS)
                .extraVerification(TEST_EXTRA_VERIFICATION)
                .prefix(TEST_PREFIX_UPDATED)
                .build();
    }

    public static Transport getTransport(){
        return Transport.builder()
                .transportId(TEST_ID)
                .transportName(TEST_TRANSPORT_NAME)
                .active(TEST_ACTIVE)
                .trackAndTraceTemplate(getTrackAndTraceTemplate())
                .contacts(new ArrayList<>(Arrays.asList(getPerson())))
                .build();
    }

    public static TransportDto getTransportDto(){
        return TransportDto.builder()
                .transportId(TEST_ID)
                .transportName(TEST_TRANSPORT_NAME)
                .active(TEST_ACTIVE)
                .trackAndTraceTemplate(getTrackAndTraceTemplateDto())
                .contacts(new ArrayList<>(Arrays.asList(getPersonDto())))
                .build();
    }

    public static TransportDto getTransportDtoNoId(){
        return TransportDto.builder()
                .transportName(TEST_TRANSPORT_NAME)
                .active(TEST_ACTIVE)
                .trackAndTraceTemplate(getTrackAndTraceTemplateDto())
                .contacts(new ArrayList<>(Arrays.asList(getPersonDto())))
                .build();
    }


    public static Transport getTransportNameUpdated(){
        return Transport.builder()
                .transportId(TEST_ID)
                .transportName(TEST_TRANSPORT_NAME_UPDATED)
                .active(TEST_ACTIVE)
                .trackAndTraceTemplate(getTrackAndTraceTemplate())
                .contacts(new ArrayList<>(Arrays.asList(getPerson())))
                .build();
    }

    public static TransportDto getTransportDtoNameUpdate(){
        return TransportDto.builder()
                .transportId(TEST_ID)
                .transportName(TEST_TRANSPORT_NAME_UPDATED)
                .active(TEST_ACTIVE)
                .trackAndTraceTemplate(getTrackAndTraceTemplateDto())
                .contacts(new ArrayList<>(Arrays.asList(getPersonDto())))
                .build();
    }

    public static Transport getTransportContactsUpdated(){
        return Transport.builder()
                .transportId(TEST_ID)
                .transportName(TEST_TRANSPORT_NAME)
                .active(TEST_ACTIVE)
                .trackAndTraceTemplate(getTrackAndTraceTemplate())
                .contacts(new ArrayList<>(Arrays.asList(getUpdatedPerson(), getNewPerson())))
                .build();
    }

    public static TransportDto getTransportDtoContactsUpdated(){
        return TransportDto.builder()
                .transportId(TEST_ID)
                .transportName(TEST_TRANSPORT_NAME)
                .active(TEST_ACTIVE)
                .trackAndTraceTemplate(getTrackAndTraceTemplateDto())
                .contacts(new ArrayList<>(Arrays.asList(getUpdatedPersonDto(), getNewPersonDto())))
                .build();
    }

    public static TransportDto getTransportDtoUnknownContact(){
        return TransportDto.builder()
                .transportId(TEST_ID)
                .transportName(TEST_TRANSPORT_NAME)
                .active(TEST_ACTIVE)
                .trackAndTraceTemplate(getTrackAndTraceTemplateDto())
                .contacts(new ArrayList<>(Arrays.asList(getUpdatedPersonDto(), getUnknownPersonDto())))
                .build();
    }
}
