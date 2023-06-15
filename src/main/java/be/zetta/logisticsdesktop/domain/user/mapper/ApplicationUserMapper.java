package be.zetta.logisticsdesktop.domain.user.mapper;

import be.zetta.logisticsdesktop.domain.notification.mapper.NotificationMapper;
import be.zetta.logisticsdesktop.domain.user.entity.ApplicationUser;
import be.zetta.logisticsdesktop.domain.user.entity.dto.ApplicationUserDto;
import be.zetta.logisticsdesktop.domain.user.entity.dto.RegisterDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ApplicationUserMapper {
    private final NotificationMapper notificationMapper;

    public ApplicationUser toEntity(RegisterDto dto) {
        return ApplicationUser.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .notifications(new ArrayList<>())
                .build();
    }

    public ApplicationUserDto toDto(ApplicationUser applicationUser) {
        return ApplicationUserDto.builder()
                .userId(applicationUser.getUserId())
                .email(applicationUser.getEmail())
                .role(applicationUser.getRole())
                .notifications(applicationUser.getNotifications().stream().map(notificationMapper::toDto).collect(Collectors.toList()))
                .build();
    }

    public ApplicationUser updateEntity(ApplicationUser toUpdate, RegisterDto updateInfo) {
        toUpdate.setEmail(updateInfo.getEmail());
        toUpdate.setPassword(updateInfo.getPassword());
        toUpdate.setRole(updateInfo.getRole());
        return toUpdate;
    }
}
