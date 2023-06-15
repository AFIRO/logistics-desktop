package be.zetta.logisticsdesktop.domain.notification.service;

import be.zetta.logisticsdesktop.domain.notification.entity.Notification;
import be.zetta.logisticsdesktop.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationService {
    public static final String NOT_EXIST = "Notification does not exist.";
    private final NotificationRepository notificationRepository;

    public void setNotificationsToSeen(String orderId){
        log.info("Setting notifications for order with Id {} to seen via service", orderId);
        List<Notification> toUpdate = notificationRepository.findAllByIdOfOrderToBeChecked(orderId);
        toUpdate.forEach(notification -> notification.setActive(false));
        notificationRepository.saveAll(toUpdate);
    }
}
