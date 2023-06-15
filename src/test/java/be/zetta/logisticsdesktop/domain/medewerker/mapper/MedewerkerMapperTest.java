package be.zetta.logisticsdesktop.domain.medewerker.mapper;

import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.entity.dto.MedewerkerDto;
import be.zetta.logisticsdesktop.domain.util.mapper.PersonMapper;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedewerkerMapperTest {
    @Mock
    private PersonMapper personMapper;
    @InjectMocks
    private MedewerkerMapper medewerkerMapper;

    @Test
    void toDto() {
        MedewerkerDto expected = MedewerkerTestData.getMedewerkerDto();
        Medewerker startData = MedewerkerTestData.getMedewerker();
        when(personMapper.toDto(startData.getPerson())).thenReturn(expected.getPerson());
        MedewerkerDto actual = medewerkerMapper.toDto(startData);

        Assertions.assertThat(actual.getPerson()).isEqualTo(expected.getPerson());
        assertThat(actual.getFunctie()).isEqualTo(expected.getFunctie());
    }

    @Test
    void toEntity() {
        Medewerker expected = MedewerkerTestData.getMedewerker();
        MedewerkerDto startData = MedewerkerTestData.getMedewerkerDto();
        when(personMapper.toEntity(startData.getPerson())).thenReturn(expected.getPerson());

        Medewerker actual = medewerkerMapper.toEntity(startData);

        Assertions.assertThat(actual.getPerson()).isEqualTo(expected.getPerson());
        assertThat(actual.getFunctie()).isEqualTo(expected.getFunctie());
    }

    @Test
    void updateEntity() {
        Medewerker expected = MedewerkerTestData.getUpdatedMedewerker();
        Medewerker startData = MedewerkerTestData.getMedewerker();
        MedewerkerDto updateInfo = MedewerkerTestData.getUpdatedMedewerkerDto();
        when(personMapper.updateEntity(startData.getPerson(), updateInfo.getPerson())).thenReturn(expected.getPerson());
        Medewerker actual = medewerkerMapper.updateEntity(startData, updateInfo);

        Assertions.assertThat(actual.getPerson()).isEqualTo(expected.getPerson());
        assertThat(actual.getFunctie()).isEqualTo(expected.getFunctie());
    }
}