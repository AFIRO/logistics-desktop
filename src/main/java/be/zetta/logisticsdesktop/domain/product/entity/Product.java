package be.zetta.logisticsdesktop.domain.product.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String productId;
    private String name;
    private double unitPrice;
    private String description;
    private int numberInStock;
    private LocalDate expectedDeliveryDate;
    @Basic(fetch = FetchType.LAZY)
    private byte[] picture;
}
