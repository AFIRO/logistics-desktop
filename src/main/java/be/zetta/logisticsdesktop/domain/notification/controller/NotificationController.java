package be.zetta.logisticsdesktop.domain.notification.controller;

import be.zetta.logisticsdesktop.domain.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    public void setNotificationsLinkedToOrderToSeen(String orderId){
        log.info("Setting notifications for order with Id {} to seen via Controller", orderId);
        notificationService.setNotificationsToSeen(orderId);
    }
}
