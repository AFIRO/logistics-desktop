package be.zetta.logisticsdesktop.domain.transport.mapper;

import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.transport.repository.TransportRepository;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import be.zetta.logisticsdesktop.domain.util.entity.dto.PersonDto;
import be.zetta.logisticsdesktop.domain.util.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransportMapper {
    private static final String ALREADY_REGISTERED = "Transport already registered with this name.";
    private static final String UNKNOWN_CONTACT_IN_DTO = "PersonId in transport update dto does not match known contacts.";
    private final TrackAndTraceMapper trackAndTraceMapper;
    private final PersonMapper personMapper;
    private final TransportRepository transportRepository;

    public TransportDto toDto(Transport entity) {
        return TransportDto.builder()
                .transportId(entity.getTransportId())
                .transportName(entity.getTransportName())
                .active(entity.isActive())
                .trackAndTraceTemplate(trackAndTraceMapper.toDto(entity.getTrackAndTraceTemplate()))
                .contacts(entity.getContacts() != null ? entity.getContacts()
                        .stream()
                        .map(personMapper::toDto)
                        .collect(Collectors.toList()): null)
                .build();
    }

    public Transport toEntity(TransportDto dto) {
        return Transport.builder()
                .transportId(dto.getTransportId())
                .active(dto.isActive())
                .transportName(dto.getTransportName())
                .trackAndTraceTemplate(dto.getTrackAndTraceTemplate() != null ? trackAndTraceMapper.toEntity(dto.getTrackAndTraceTemplate()) : null)
                .contacts(dto.getContacts() != null ? dto.getContacts().stream().map(personMapper::toEntity).collect(Collectors.toList()) : null)
                .build();
    }

    public Transport updateEntity(Transport toUpdate, TransportDto updateInfo) {
        updateNameIfUnique(toUpdate, updateInfo);
        toUpdate.setActive(updateInfo.isActive());
        toUpdate.setTrackAndTraceTemplate(trackAndTraceMapper.updateEntity(toUpdate.getTrackAndTraceTemplate(),updateInfo.getTrackAndTraceTemplate()));
        toUpdate.setContacts(updateInfo.getContacts() != null ? updateContactList(toUpdate.getContacts(), updateInfo.getContacts()) : null);
        return toUpdate;
    }

    private void updateNameIfUnique(Transport toUpdate, TransportDto updateInfo) {
        if (!toUpdate.getTransportName().equals(updateInfo.getTransportName())) {
            if (!transportRepository.existsByTransportName(updateInfo.getTransportName())) {
                toUpdate.setTransportName(updateInfo.getTransportName());
            } else {
                throw new IllegalArgumentException(ALREADY_REGISTERED);
            }
        }
    }

    private List<Person> updateContactList(List<Person> toUpdate, List<PersonDto> updateInfo) {
        for (PersonDto updatePersonInfo : updateInfo) {
            if (updatePersonInfo.getPersonId() == null || updatePersonInfo.getPersonId().isBlank()) {
                toUpdate.add(personMapper.toEntity(updatePersonInfo));
            } else {
                Person personToUpdate = toUpdate.stream().filter(person -> person.getPersonId().equals(updatePersonInfo.getPersonId())).findFirst().orElseThrow(() -> new IllegalArgumentException(UNKNOWN_CONTACT_IN_DTO));
                Person updatedPerson = personMapper.updateEntity(personToUpdate, updatePersonInfo);
                toUpdate.set(toUpdate.indexOf(personToUpdate), updatedPerson);
            }
        }
        return toUpdate;
    }

}
