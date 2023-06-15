package be.zetta.logisticsdesktop.domain.user.entity.dto;

import be.zetta.logisticsdesktop.domain.notification.entity.dto.NotificationDto;
import be.zetta.logisticsdesktop.domain.user.entity.UserRole;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ApplicationUserDto {
    private String userId;
    private String email;
    private UserRole role;
    private List<NotificationDto> notifications;
}
