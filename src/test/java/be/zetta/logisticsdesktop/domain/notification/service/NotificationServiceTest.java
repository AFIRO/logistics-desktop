package be.zetta.logisticsdesktop.domain.notification.service;

import be.zetta.logisticsdesktop.domain.notification.entity.Notification;
import be.zetta.logisticsdesktop.domain.notification.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static be.zetta.logisticsdesktop.domain.notification.NotificationTestData.TEST_ID;
import static be.zetta.logisticsdesktop.domain.notification.NotificationTestData.getNotification;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
    @Mock
    private NotificationRepository notificationRepository;
    @InjectMocks
    private NotificationService notificationService;
    @Captor
    private ArgumentCaptor<List<Notification>> notificationCaptor;

    @Test
    void setNotificationToSeen_happyFlow(){
        Notification listContents = getNotification();
        List<Notification> startData = Arrays.asList(listContents);
        when(notificationRepository.findAllByIdOfOrderToBeChecked(TEST_ID)).thenReturn(startData);
        notificationService.setNotificationsToSeen(TEST_ID);

        verify(notificationRepository,times(1)).saveAll(notificationCaptor.capture());
        List<Notification> actual = notificationCaptor.getValue();
        assertThat(actual).isNotEmpty();
        assertThat(actual.get(0).isActive()).isFalse();
    }

    @Test
    void setNotificationToSeen_noNotifications(){
        when(notificationRepository.findAllByIdOfOrderToBeChecked(TEST_ID)).thenReturn(new ArrayList<>());
        notificationService.setNotificationsToSeen(TEST_ID);

        verify(notificationRepository,times(1)).saveAll(notificationCaptor.capture());
        List<Notification> actual = notificationCaptor.getValue();
        assertThat(actual).isEmpty();
    }
}