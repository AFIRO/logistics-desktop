package be.zetta.logisticsdesktop.domain.order.entity;

import be.zetta.logisticsdesktop.domain.product.entity.Product;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String lineId;
    @ManyToOne(cascade = CascadeType.ALL)
    private Product product;
    private Integer quantityOrdered;
    private double unitPriceOrderLine;
}
