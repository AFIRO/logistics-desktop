package be.zetta.logisticsdesktop.domain.notification.controller;

import be.zetta.logisticsdesktop.domain.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static be.zetta.logisticsdesktop.domain.notification.NotificationTestData.TEST_ID;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {
    @Mock
    private NotificationService notificationService;
    @InjectMocks
    private NotificationController notificationController;
    @Test
    void setNotificationsLinkedToOrderToSeen_happyFlow() {
        notificationController.setNotificationsLinkedToOrderToSeen(TEST_ID);
        verify(notificationService,times(1)).setNotificationsToSeen(TEST_ID);
    }
}