package be.zetta.logisticsdesktop.domain.user.entity;

import be.zetta.logisticsdesktop.domain.notification.entity.Notification;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationUser {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String userId;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Notification> notifications;
    @OneToOne
    private Person persoonsgegevens;
}
