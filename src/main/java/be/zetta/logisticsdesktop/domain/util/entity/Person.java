package be.zetta.logisticsdesktop.domain.util.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String personId;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
}
