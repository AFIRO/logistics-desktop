package be.zetta.logisticsdesktop.domain.medewerker.mapper;

import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.entity.dto.MedewerkerDto;
import be.zetta.logisticsdesktop.domain.util.mapper.PersonMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MedewerkerMapper {

    private final PersonMapper personMapper;

    public MedewerkerDto toDto(Medewerker medewerker) {
        return MedewerkerDto.builder()
                .person(personMapper.toDto(medewerker.getPerson()))
                .functie(medewerker.getFunctie())
                .build();
    }

    public Medewerker toEntity(MedewerkerDto medewerkerDto) {
        return Medewerker.builder()
                .person(personMapper.toEntity(medewerkerDto.getPerson()))
                .functie(medewerkerDto.getFunctie())
                .build();
    }

    public Medewerker updateEntity(Medewerker toUpdate, MedewerkerDto updateInfo) {
        toUpdate.setFunctie(updateInfo.getFunctie());
        toUpdate.setPerson(personMapper.updateEntity(toUpdate.getPerson(), updateInfo.getPerson()));
        return toUpdate;
    }
}
