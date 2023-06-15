package be.zetta.logisticsdesktop.domain.util;


import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.repository.CustomerRepository;
import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.repository.MedewerkerRepository;
import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.entity.OrderLine;
import be.zetta.logisticsdesktop.domain.order.entity.OrderStatus;
import be.zetta.logisticsdesktop.domain.order.repository.OrderRepository;
import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.PackagingType;
import be.zetta.logisticsdesktop.domain.packaging.repository.PackagingRepository;
import be.zetta.logisticsdesktop.domain.product.entity.Product;
import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceExtraVerification;
import be.zetta.logisticsdesktop.domain.transport.entity.TrackAndTraceTemplate;
import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.user.entity.ApplicationUser;
import be.zetta.logisticsdesktop.domain.user.entity.UserRole;
import be.zetta.logisticsdesktop.domain.user.repository.UserRepository;
import be.zetta.logisticsdesktop.domain.util.entity.Address;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ConditionalOnProperty(name = "development-mode")
@Component
@RequiredArgsConstructor
public class DataLoader {
    private final MedewerkerRepository medewerkerRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository customerOrderRepository;
    private final PackagingRepository packagingRepository;

    @PostConstruct
    private void loadData() {
        seedApplicationUsers();
        seedMedewerkers();
        seedCustomers();
        seedCustomerOrders();
        seedPackaging();
        printContentsOfDB();
    }

    private void printContentsOfDB() {
        userRepository.findAll().forEach(System.out::println);
        medewerkerRepository.findAll().forEach(System.out::println);
    }

    private void seedMedewerkers() {
        //seed medewerkers
        Medewerker andreeas = Medewerker.builder()
                .functie("Developer")
                .person(Person.builder()
                        .firstName("Andreeas")
                        .lastName("Firoiu")
                        .phoneNumber("12345")
                        .email("andreeas@hogent.be")
                        .address(Address.builder()
                                .street("Fakestreet")
                                .houseNumber("123")
                                .postalCode("1234")
                                .country("Belgium")
                                .build())
                        .build())
                .build();

        Medewerker freia = Medewerker.builder()
                .functie("Developer")
                .person(Person.builder()
                        .firstName("Freia")
                        .lastName("Van Loon")
                        .phoneNumber("12345")
                        .email("freia@hogent.be")
                        .address(Address.builder()
                                .street("Fakestreet")
                                .houseNumber("123")
                                .postalCode("1234")
                                .country("Belgium")
                                .build())
                        .build())
                .build();

        Medewerker lucas = Medewerker.builder()
                .functie("Developer")
                .person(Person.builder()
                        .firstName("Lucas")
                        .lastName("Van Compernolle")
                        .phoneNumber("12345")
                        .email("lucas@hogent.be")
                        .address(Address.builder()
                                .street("Fakestreet")
                                .houseNumber("123")
                                .postalCode("1234")
                                .country("Belgium")
                                .build())
                        .build())
                .build();

        Medewerker kasper = Medewerker.builder()
                .functie("Developer")
                .person(Person.builder()
                        .firstName("Kasper")
                        .lastName("Van Remoortere")
                        .phoneNumber("12345")
                        .email("kasper@hogent.be")
                        .address(Address.builder()
                                .street("Fakestreet")
                                .houseNumber("123")
                                .postalCode("1234")
                                .country("Belgium")
                                .build())
                        .build())
                .build();
        medewerkerRepository.saveAll(Arrays.asList(andreeas, freia, lucas, kasper));
    }

    private void seedApplicationUsers() {
        //seed application users
        ApplicationUser andreeas = ApplicationUser.builder()
                .email("andreeas@hogent.be")
                .password("$2a$12$SbEl/7FgBeX03IwfUnpk0.sfbGasn.yyhHxmE8FabulBgGO46g/QO")
                .role(UserRole.ADMIN)
                .notifications(new ArrayList<>())
                .build();

        ApplicationUser freia = ApplicationUser.builder()
                .email("freia@hogent.be")
                .password("$2a$12$2XcHDtp4y78JNScEip4hs.rqEsFtROMLfihQ.z7UlrlchGXjzw3Zq")
                .role(UserRole.ADMIN)
                .notifications(new ArrayList<>())
                .build();
        ApplicationUser lucas = ApplicationUser.builder()
                .email("lucas@hogent.be")
                .password("$2a$12$KQ8kPjKB/wTGPj2VqL6GOuHySpG4DRvJkdta/D2BpAzA5fOtS.x/m")
                .role(UserRole.ADMIN)
                .notifications(new ArrayList<>())
                .build();
        ApplicationUser kasper = ApplicationUser.builder()
                .email("kasper@hogent.be")
                .password("$2a$12$I4W1grS7GhnDY/g5Jkt3hOwLckPg9x2vGikBAbfdyBGm3MYeamDFO")
                .role(UserRole.MAGAZIJNIER)
                .notifications(new ArrayList<>())
                .build();

        userRepository.saveAll(Arrays.asList(andreeas, freia, lucas, kasper));
    }

    private void seedCustomerOrders() {
        Customer cust1 = customerRepository.findAll().get(0);

        CustomerOrder order1 = CustomerOrder.builder()
                .orderId("TEST_ID")
                .customer(cust1)
                .orderLines(getOrderLines())
                .packaging(Packaging.builder().packagingName("TEST1").build())
                .transport(Transport.builder().transportName("DHL").active(true)
                        .contacts(List.of(Person.builder().firstName("Piet").lastName("Uytebroeck").email("jan@hogent.be").phoneNumber("04154856546").build()))
                        .trackAndTraceTemplate(TrackAndTraceTemplate.builder().extraVerification(TrackAndTraceExtraVerification.POSTALCODE).prefix("DHL-").numberOfChars(10).build()).build())
                .trackTraceCode(TrackAndTraceTemplate.builder().prefix("dhl").build().generateTrackAndTraceCode())
                .deliveryAddress(Address.builder().street("test").houseNumber("15").build())
                .orderDate(LocalDate.now())
                .purchaser(Person.builder().firstName("Jan").lastName("Janssens").email("jan@test.be").build())
                .status(OrderStatus.SENT)
                .build();
        CustomerOrder order2 = CustomerOrder.builder()
                .orderId("TEST_ID")
                .customer(cust1)
                .orderLines(getOrderLines())
                .transport(Transport.builder().transportName("FEDEX").active(true).trackAndTraceTemplate(TrackAndTraceTemplate.builder().extraVerification(TrackAndTraceExtraVerification.ORDERID).prefix("FED-").numberOfChars(10).build()).build())
                .trackTraceCode("")
                .packaging(Packaging.builder().packagingName("TEST2").build())
                .deliveryAddress(Address.builder().street("test").houseNumber("15").build())
                .orderDate(LocalDate.now())
                .purchaser(Person.builder().firstName("Jan").lastName("Janssens1").email("jan@test.be").build())
                .status(OrderStatus.SENT)
                .build();
        CustomerOrder order3 = CustomerOrder.builder()
                .orderId("TEST_ID")
                .customer(cust1)
                .orderLines(getOrderLines())
                .transport(Transport.builder().transportName("TEST_123").active(false).trackAndTraceTemplate(TrackAndTraceTemplate.builder().build()).build())
                .trackTraceCode(TrackAndTraceTemplate.builder().extraVerification(TrackAndTraceExtraVerification.POSTALCODE).prefix("test").build().generateTrackAndTraceCode())
                .packaging(Packaging.builder().packagingName("TEST3").build())
                .deliveryAddress(Address.builder().street("test").houseNumber("15").build())
                .orderDate(LocalDate.now())
                .purchaser(Person.builder().firstName("Jan").lastName("Janssens2").email("jan@test.be").build())
                .status(OrderStatus.SENT)
                .build();
        CustomerOrder order4 = CustomerOrder.builder()
                .orderId("TEST_ID")
                .customer(cust1)
                .orderLines(getOrderLines())
                .transport(Transport.builder().transportName("DHL-2").active(true).trackAndTraceTemplate(TrackAndTraceTemplate.builder().extraVerification(TrackAndTraceExtraVerification.POSTALCODE).build()).build())
                .trackTraceCode(TrackAndTraceTemplate.builder().prefix("dhl2").build().generateTrackAndTraceCode())
                .packaging(Packaging.builder().packagingName("TEST4").build())
                .deliveryAddress(Address.builder().street("test").houseNumber("15").build())
                .orderDate(LocalDate.now())
                .purchaser(Person.builder().firstName("Jan").lastName("Janssens5").email("jan@test.be").build())
                .status(OrderStatus.OPEN)
                .build();

        customerOrderRepository.saveAll(List.of(order1, order2, order3, order4));
    }

    private void seedCustomer(){
        Customer cust1 = Customer.builder()
                .customerId("TEST_ID")
                .phoneNumber("+55123456789")
                .name("dummy")
                .build();

        customerRepository.save(cust1);
    }

    private void seedCustomers() {
        //seed application users
        Customer hogent = Customer.builder()
                .name("Hogent")
                .phoneNumber("0412345678")
                .logo(new byte[]{-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82,
                        0, 0, 0, 15, 0, 0, 0, 15, 8, 6, 0, 0, 0, 59, -42, -107,
                        74, 0, 0, 0, 64, 73, 68, 65, 84, 120, -38, 99, 96, -64, 14, -2,
                        99, -63, 68, 1, 100, -59, -1, -79, -120, 17, -44, -8, 31, -121, 28, 81,
                        26, -1, -29, 113, 13, 78, -51, 100, -125, -1, -108, 24, 64, 86, -24, -30,
                        11, 101, -6, -37, 76, -106, -97, 25, 104, 17, 96, -76, 77, 97, 20, -89,
                        109, -110, 114, 21, 0, -82, -127, 56, -56, 56, 76, -17, -42, 0, 0, 0,
                        0, 73, 69, 78, 68, -82, 66, 96, -126})
                .address(Address.builder()
                        .street("Fakestreet")
                        .houseNumber("123")
                        .postalCode("1234")
                        .country("Belgium")
                        .build())
                .buyers(List.of(Person.builder()
                        .firstName("Azerty")
                        .lastName("Qwerty")
                        .email("azerty@qwerty.be")
                        .phoneNumber("031234567")
                        .address(Address.builder()
                                .street("Fakestreet")
                                .houseNumber("123")
                                .postalCode("1234")
                                .country("Belgium")
                                .build())
                        .build()))
                .build();

        Customer ugent = Customer.builder()
                .name("UGent")
                .phoneNumber("0412345678")
                .logo(new byte[]{-119, 80, 78, 71, 13, 10, 26, 10, 0, 0, 0, 13, 73, 72, 68, 82,
                        0, 0, 0, 15, 0, 0, 0, 15, 8, 6, 0, 0, 0, 59, -42, -107,
                        74, 0, 0, 0, 64, 73, 68, 65, 84, 120, -38, 99, 96, -64, 14, -2,
                        99, -63, 68, 1, 100, -59, -1, -79, -120, 17, -44, -8, 31, -121, 28, 81,
                        26, -1, -29, 113, 13, 78, -51, 100, -125, -1, -108, 24, 64, 86, -24, -30,
                        11, 101, -6, -37, 76, -106, -97, 25, 104, 17, 96, -76, 77, 97, 20, -89,
                        109, -110, 114, 21, 0, -82, -127, 56, -56, 56, 76, -17, -42, 0, 0, 0,
                        0, 73, 69, 78, 68, -82, 66, 96, -126})
                .address(Address.builder()
                        .street("Fakestreet")
                        .houseNumber("123")
                        .postalCode("1234")
                        .country("Belgium")
                        .build())
                .build();

        customerRepository.saveAll(Arrays.asList(hogent, ugent));
    }

    private List<OrderLine> getOrderLines() {


        return List.of(OrderLine.builder()
                        .lineId("")
                        .unitPriceOrderLine(15)
                        .product(Product.builder().name("appel").build())
                        .quantityOrdered(10)
                        .build(),
                OrderLine.builder()
                        .lineId("")
                        .unitPriceOrderLine(1.5)
                        .product(Product.builder().name("druif").build())
                        .quantityOrdered(11)
                        .build(),
                OrderLine.builder()
                        .lineId("")
                        .unitPriceOrderLine(15.6)
                        .product(Product.builder().name("banaan").build())
                        .quantityOrdered(5)
                        .build());


    }

    private void seedPackaging(){
        Packaging smallBox = Packaging.builder()
                .packagingName("kleine doos")
                .active(true)
                .price(4.95)
                .height(7.4)
                .width(15.5)
                .length(23)
                .type(PackagingType.STANDARD)
                .build();

        Packaging mediumBox = Packaging.builder()
                .packagingName("medium doos")
                .active(true)
                .price(6.95)
                .height(9.8)
                .width(22)
                .length(31)
                .type(PackagingType.STANDARD)
                .build();

        Packaging largeBox = Packaging.builder()
                .packagingName("grote doos")
                .active(true)
                .price(11.95)
                .height(13.5)
                .width(29)
                .length(38.5)
                .type(PackagingType.STANDARD)
                .build();

        Packaging customBox = Packaging.builder()
                .packagingName("vierkante doos")
                .active(true)
                .price(7.95)
                .height(25)
                .width(25)
                .length(25)
                .type(PackagingType.CUSTOM)
                .build();

        packagingRepository.saveAll(List.of(smallBox, mediumBox, largeBox, customBox));

    }


}
