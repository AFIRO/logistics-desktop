package be.zetta.logisticsdesktop.domain.notification;

import be.zetta.logisticsdesktop.domain.notification.entity.Notification;
import be.zetta.logisticsdesktop.domain.notification.entity.dto.NotificationDto;

import java.time.LocalDate;

public class NotificationTestData {

    public static final String TEST_ID = "TEST_ID";
    public static final String TEST_ORDER_ID = "TEST_ORDER_ID";
    public static final boolean TEST_ACTIVE = true;
    public static final LocalDate TEST_DATE_CREATED = LocalDate.now();
    public static final String NOT_EXIST = "Notification does not exist.";

    public static Notification getNotification(){
        return Notification.builder()
                .id(TEST_ID)
                .active(TEST_ACTIVE)
                .dateCreated(TEST_DATE_CREATED)
                .idOfOrderToBeChecked(TEST_ORDER_ID)
                .build();
    }

    public static NotificationDto getNotificationDto(){
        return NotificationDto.builder()
                .id(TEST_ID)
                .active(TEST_ACTIVE)
                .dateCreated(TEST_DATE_CREATED)
                .idOfOrderToBeChecked(TEST_ORDER_ID)
                .build();
    }
}
