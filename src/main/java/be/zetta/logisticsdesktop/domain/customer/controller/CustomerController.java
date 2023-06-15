package be.zetta.logisticsdesktop.domain.customer.controller;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@Log4j2
public class CustomerController {
    public static final String DTO_ID_NOT_EMPTY = "Dto Id must be empty for create.";
    public static final String ID_MUST_MATCH_ID_IN_DTO = "Id must match Id in Dto.";
    private final CustomerService customerService;
    private final Validator validator;

    public List<Customer> getAllCustomers() {
        log.info("Get All Customers called from Controller.");
        return customerService.getAll();
    }

    public Customer getById(String id) {
        log.info("Get By Id for id {} Customer called from Controller.", id);
        return customerService.getById(id);
    }

    public Customer createCustomer(CustomerDto data) {
        validateDto(data);
        log.info("Create Customer called from Controller.");
        if (data.getCustomerId() != null && !data.getCustomerId().isBlank()) {
            log.error(DTO_ID_NOT_EMPTY);
            throw new IllegalArgumentException(DTO_ID_NOT_EMPTY);
        }
        return customerService.createCustomer(data);
    }

    public Customer updateCustomer(String id, CustomerDto data) {
        validateDto(data);

        log.info("Update for id {} Customer called from Controller.", id);
        if (!id.equals(data.getCustomerId())) {
            log.error(ID_MUST_MATCH_ID_IN_DTO);
            throw new IllegalArgumentException(ID_MUST_MATCH_ID_IN_DTO);
        }
        return customerService.updateCustomer(id, data);
    }

    public Customer deleteCustomer(String id) {
        log.info("Delete for id {} Customer called from Controller.", id);
        return customerService.deleteCustomer(id);
    }

    private void validateDto(CustomerDto data) {
        Set<ConstraintViolation<CustomerDto>> violations = validator.validate(data);
        if (!violations.isEmpty()) {
            String errormessage = violations
                    .stream()
                    .map(ConstraintViolation::getMessage)
                    .findFirst()
                    .get();
            log.error(errormessage);
            throw new IllegalArgumentException(errormessage);
        }
    }
}
