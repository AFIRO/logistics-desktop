package be.zetta.logisticsdesktop.domain.notification.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Notification {
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Id
    private String id;
    private LocalDate dateCreated;
    private String idOfOrderToBeChecked;
    @Column(columnDefinition = "boolean default true")
    private boolean active;

    public Notification(String idOfOrderToBeChecked){
        this.idOfOrderToBeChecked = idOfOrderToBeChecked;
        this.active = true;
        this.dateCreated = LocalDate.now();
    }
}
