package be.zetta.logisticsdesktop.gui.magazijnier;

import be.zetta.logisticsdesktop.domain.customer.controller.CustomerController;
import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.customer.mapper.CustomerMapper;
import be.zetta.logisticsdesktop.domain.order.entity.CustomerOrder;
import be.zetta.logisticsdesktop.domain.order.mapper.OrderMapper;
import be.zetta.logisticsdesktop.domain.util.entity.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KlantenModel {
    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private OrderMapper orderMapper;
    private List<Customer> klanten;
    private Customer customerDetail;
    private ObservableList<String> klantenNamen = FXCollections.observableArrayList();
    private ObservableList<Person> aankopers = FXCollections.observableArrayList();
    private ObservableList<CustomerOrder> bestellingen = FXCollections.observableArrayList();

    // GETTERS
    public ObservableList<String> getKlantenNamen(){
        updateKlanten();
        return this.klantenNamen;
    }
    public Customer getCustomerDetail() {
        return this.customerDetail;
    }
    public ObservableList<Person> getAankopers() {
        return this.aankopers;
    }
    public ObservableList<CustomerOrder> getBestellingen() {
        return this.bestellingen;
    }
    // SETTERS
    public void setCustomerDetailFromIndex(int index) {
        if (index < 0) return;
        this.customerDetail = klanten.get(index);
        aankopers.clear();
        customerDetail.getBuyers().forEach(aankoper -> aankopers.add(aankoper));
        bestellingen.clear();
        customerDetail.getOrders().forEach((bestelling -> bestellingen.add(bestelling)));
    }

    // UPDATE OTHER PANES
    public void updateKlanten() {
        klanten = customerController.getAllCustomers();
        klantenNamen.clear();
        klanten.stream().forEach(klant -> klantenNamen.add(String.format("%s - Aantal bestellingen: %d", klant.getName(),  klant.getOrders().size())));
    }

    // CRUD
    public void updateKlant(Customer customer) {
        CustomerDto dto = customerMapper.toDto(customer);
        dto.setOrders(customer.getOrders().stream().map(orderMapper::toDto).collect(Collectors.toList()));
        customerController.updateCustomer(customer.getCustomerId(), dto);
    }
    public void addKlant(Customer customer) {
        CustomerDto dto = customerMapper.toDto(customer);
        dto.setOrders(new ArrayList<>());
        customerController.createCustomer(dto);
    }
    public  void deleteKlant(Customer customer) {
        customerController.deleteCustomer(customer.getCustomerId());
    }
}
