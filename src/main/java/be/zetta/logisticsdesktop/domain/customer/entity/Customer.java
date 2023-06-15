package be.zetta.logisticsdesktop.domain.customer.entity;

import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.util.entity.Address;
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
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String customerId;
    private String name;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] logo;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    private String phoneNumber;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Person> buyers;
    @OneToMany(mappedBy = "customer")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CustomerOrder> orders;
}
