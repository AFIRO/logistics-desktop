package be.zetta.logisticsdesktop.domain.order.entity;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.util.entity.Address;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrder {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String orderId;
    private LocalDate orderDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Address deliveryAddress;
    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderLine> orderLines;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne(cascade = CascadeType.ALL)
    private Customer customer;
    @OneToOne(cascade = CascadeType.ALL)
    private Person purchaser;
    @OneToOne(cascade = CascadeType.ALL)
    private Packaging packaging;
    @ManyToOne(cascade = CascadeType.ALL)
    private Transport transport;
    private String trackTraceCode;
    private String extraValidationCode;

    public void generateTrackAndTraceCodeAndExtraValidationCode() {
        if (this.transport != null) {
            this.trackTraceCode = transport.getTrackAndTraceTemplate().generateTrackAndTraceCode();
            switch (this.transport.getTrackAndTraceTemplate().getExtraVerification()){

                case POSTALCODE -> {
                    this.extraValidationCode = this.deliveryAddress.getPostalCode();
                }
                case ORDERID -> {
                    this.extraValidationCode = this.orderId;
                }
            }
        }
    }
}


