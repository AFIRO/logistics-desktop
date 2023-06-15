package be.zetta.logisticsdesktop.domain.packaging.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor

public class Packaging {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String packagingId;
    @Column(unique = true)
    private String packagingName;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(columnDefinition = "varchar(255) default 'STANDARD'")
    private PackagingType type = PackagingType.STANDARD;
    private double height;
    private double width;
    private double length;
    private double price;
    @Column(columnDefinition = "boolean default true")
    private boolean active = true;

}
