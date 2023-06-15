package be.zetta.logisticsdesktop.domain.notification.mapper;

import be.zetta.logisticsdesktop.domain.notification.entity.Notification;
import be.zetta.logisticsdesktop.domain.notification.entity.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationDto toDto(Notification notification){
        return NotificationDto.builder()
                .id(notification.getId())
                .dateCreated(notification.getDateCreated())
                .idOfOrderToBeChecked(notification.getIdOfOrderToBeChecked())
                .active(notification.isActive())
                .build();
    }

    public Notification toEntity(NotificationDto notification){
        return Notification.builder()
                .id(notification.getId())
                .dateCreated(notification.getDateCreated())
                .idOfOrderToBeChecked(notification.getIdOfOrderToBeChecked())
                .active(notification.isActive())
                .build();
    }
}
