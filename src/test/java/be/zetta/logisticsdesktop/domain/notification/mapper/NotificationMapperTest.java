package be.zetta.logisticsdesktop.domain.notification.mapper;

import be.zetta.logisticsdesktop.domain.notification.entity.Notification;
import be.zetta.logisticsdesktop.domain.notification.entity.dto.NotificationDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.notification.NotificationTestData.getNotification;
import static be.zetta.logisticsdesktop.domain.notification.NotificationTestData.getNotificationDto;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class NotificationMapperTest {
    @InjectMocks
    private NotificationMapper notificationMapper;

    @Test
    void toDto() {
        NotificationDto expected = getNotificationDto();
        NotificationDto actual = notificationMapper.toDto(getNotification());

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getDateCreated()).isEqualTo(expected.getDateCreated());
        assertThat(actual.getIdOfOrderToBeChecked()).isEqualTo(expected.getIdOfOrderToBeChecked());
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
    }

    @Test
    void ToEntity() {
        Notification expected = getNotification();
        Notification actual = notificationMapper.toEntity(getNotificationDto());

        assertThat(actual.getId()).isEqualTo(expected.getId());
        assertThat(actual.getDateCreated()).isEqualTo(expected.getDateCreated());
        assertThat(actual.getIdOfOrderToBeChecked()).isEqualTo(expected.getIdOfOrderToBeChecked());
        assertThat(actual.isActive()).isEqualTo(expected.isActive());
    }
}