package be.zetta.logisticsdesktop.domain.transport.entity;

import be.zetta.logisticsdesktop.domain.util.entity.Person;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Transport {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String transportId;
    @Column(unique = true)
    private String transportName;
    @OneToMany(cascade = CascadeType.ALL)
    @ToString.Exclude
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Person> contacts;
    @Column(columnDefinition = "boolean default true")
    private boolean active;
    @OneToOne(cascade = CascadeType.ALL)
    private TrackAndTraceTemplate trackAndTraceTemplate;
}
