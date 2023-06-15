package be.zetta.logisticsdesktop.domain.user.mapper;

import be.zetta.logisticsdesktop.domain.notification.mapper.NotificationMapper;
import be.zetta.logisticsdesktop.domain.user.entity.ApplicationUser;
import be.zetta.logisticsdesktop.domain.user.entity.dto.ApplicationUserDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.user.UserTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ApplicationUserMapperTest {
    @Mock
    private NotificationMapper notificationMapper;
    @InjectMocks
    private ApplicationUserMapper applicationUserMapper;

    @Test
    void toEntity() {
        ApplicationUser expected = getApplicationUserNoId();
        ApplicationUser actual = applicationUserMapper.toEntity(getRegisterDto());

        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
        assertThat(actual.getRole()).isEqualTo(expected.getRole());
        assertThat(actual.getUserId()).isEqualTo(actual.getUserId());
    }

    @Test
    void toDto() {
        ApplicationUserDto expected = getApplicationUserDto();

        ApplicationUserDto actual = applicationUserMapper.toDto(getApplicationUser());

        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        Assertions.assertThat(actual.getRole()).isEqualTo(expected.getRole());
        assertThat(actual.getUserId()).isEqualTo(actual.getUserId());

    }

    @Test
    void updateEntity() {
        ApplicationUser expected = getUpdatedApplicationUser();
        ApplicationUser actual = applicationUserMapper.updateEntity(getApplicationUser(), getUpdatedRegisterDto());

        assertThat(actual.getEmail()).isEqualTo(expected.getEmail());
        assertThat(actual.getPassword()).isEqualTo(expected.getPassword());
        assertThat(actual.getRole()).isEqualTo(expected.getRole());
        assertThat(actual.getUserId()).isEqualTo(actual.getUserId());
    }
}