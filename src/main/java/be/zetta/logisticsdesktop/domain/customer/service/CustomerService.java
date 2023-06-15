package be.zetta.logisticsdesktop.domain.customer.service;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.customer.mapper.CustomerMapper;
import be.zetta.logisticsdesktop.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CustomerService {
    public static final String NOT_FOUND = "Customer not found";
    public static final String ALREADY_EXISTS = "Customer already exists";
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public List<Customer> getAll() {
        log.info("Get All Customers called from Service.");
        return customerRepository.findAll();
    }

    public Customer getById(String id) {
        log.info("Get By Id for id {} Customer called from Service.", id);
        return customerRepository.findById(id).orElseThrow(() -> {
            log.error("Customer with Id {} not found in database", id);
            return new IllegalArgumentException(NOT_FOUND);
        });
    }

    public Customer createCustomer(CustomerDto dto) {
        log.info("Create Customer called from Service.");
        if (!customerRepository.existsByName(dto.getName())) {
            return customerRepository.save(customerMapper.toEntity(dto));
        } else {
            log.error(ALREADY_EXISTS);
            throw new IllegalArgumentException(ALREADY_EXISTS);
        }
    }

    public Customer updateCustomer(String id, CustomerDto dto) {
        log.info("Update for id {} Customer called from Service.", id);
        if (customerRepository.existsById(id)) {
            Customer toUpdate = customerRepository.findById(id).orElseThrow(() -> {
                log.error("Customer with Id {} not found in database", id);
                return new IllegalArgumentException(NOT_FOUND);
            });
            return customerRepository.save(customerMapper.updateEntity(toUpdate, dto));
        } else {
            log.error("Customer with Id {} not found in database", id);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }

    public Customer deleteCustomer(String id) {
        log.info("Delete for id {} Customer called from Service.", id);
        if (customerRepository.existsById(id)) {
            Customer deletedCustomer = customerRepository.findById(id).orElseThrow(() -> {
                log.error("Customer with Id {} not found in database", id);
                return new IllegalArgumentException(NOT_FOUND);
            });
            customerRepository.deleteById(id);
            return deletedCustomer;
        } else {
            log.error("Customer with Id {} not found in database", id);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }
}
