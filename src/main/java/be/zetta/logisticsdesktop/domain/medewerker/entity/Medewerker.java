package be.zetta.logisticsdesktop.domain.medewerker.entity;

import be.zetta.logisticsdesktop.domain.util.entity.Person;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Medewerker {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String medewerkerId;
    @OneToOne(cascade = CascadeType.ALL)
    private Person person;
    private String functie;
}
