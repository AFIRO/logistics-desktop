package be.zetta.logisticsdesktop.domain.customer.controller;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.customer.service.CustomerService;
import be.zetta.logisticsdesktop.domain.customer.CustomerTestData;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @Mock
    private Validator validator;
    @InjectMocks
    private CustomerController customerController;

    @Test
    void getAllCustomer() {
        Customer listContents = CustomerTestData.getCustomer();
        List<Customer> expected = List.of(listContents);
        when(customerService.getAll()).thenReturn(expected);
        List<Customer> actual = customerController.getAllCustomers();

        assertThat(actual).containsExactly(listContents);
        verify(customerService, times(1)).getAll();
    }

    @Test
    void getById() {
        Customer expected = CustomerTestData.getCustomer();
        when(customerService.getById(MedewerkerTestData.TEST_ID)).thenReturn(expected);
        Customer actual = customerController.getById(MedewerkerTestData.TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(customerService, times(1)).getById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void createCustomer_happyflow() {
        Customer expected = CustomerTestData.getCustomer();
        CustomerDto startData = CustomerTestData.getCreateCustomerDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(customerService.createCustomer(startData)).thenReturn(expected);

        Customer actual = customerController.createCustomer(startData);
        assertThat(actual).isEqualTo(expected);
        verify(customerService, times(1)).createCustomer(startData);
    }

    @Test
    void createCustomer_IdNotEmpty_throws() {
        CustomerDto startData = CustomerTestData.getCustomerDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerController.createCustomer(startData));
        assertThat(exception.getMessage()).isEqualTo(CustomerTestData.DTO_ID_NOT_EMPTY);
        verify(customerService, times(0)).createCustomer(startData);
    }

    @Test
    void updateCustomer_happyflow() {
        Customer expected = CustomerTestData.getUpdatedNameCustomer();
        CustomerDto updateInfo = CustomerTestData.getCustomerNameUpdatedDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(customerService.updateCustomer(MedewerkerTestData.TEST_ID, updateInfo)).thenReturn(expected);
        Customer actual = customerController.updateCustomer(MedewerkerTestData.TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(customerService, times(1)).updateCustomer(MedewerkerTestData.TEST_ID, updateInfo);
    }

    @Test
    void updateCustomer_IdDoesNotMatch_throws() {
        CustomerDto updateInfo = CustomerTestData.getCustomerNameUpdatedDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerController.updateCustomer("WRONG", updateInfo));
        assertThat(exception.getMessage()).isEqualTo(CustomerTestData.ID_MUST_MATCH_ID_IN_DTO);
        verify(customerService, times(0)).updateCustomer(MedewerkerTestData.TEST_ID, updateInfo);
    }

    @Test
    void deleteCustomer() {
        Customer expected = CustomerTestData.getCustomer();
        when(customerService.deleteCustomer(MedewerkerTestData.TEST_ID)).thenReturn(expected);
        Customer actual = customerController.deleteCustomer(MedewerkerTestData.TEST_ID);

        assertThat(actual).isEqualTo(expected);
        verify(customerService, times(1)).deleteCustomer(MedewerkerTestData.TEST_ID);
    }

}