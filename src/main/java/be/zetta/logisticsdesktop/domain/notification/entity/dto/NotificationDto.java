package be.zetta.logisticsdesktop.domain.notification.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class NotificationDto {
    private String id;
    private LocalDate dateCreated;
    private String idOfOrderToBeChecked;
    private boolean active;
}
