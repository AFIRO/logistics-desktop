package be.zetta.logisticsdesktop.domain.customer.service;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import be.zetta.logisticsdesktop.domain.customer.entity.dto.CustomerDto;
import be.zetta.logisticsdesktop.domain.customer.mapper.CustomerMapper;
import be.zetta.logisticsdesktop.domain.customer.repository.CustomerRepository;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.customer.CustomerTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerMapper customerMapper;
    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAll_happyFlow() {
        Customer listContents = getCustomer();
        List<Customer> expected = List.of(listContents);
        when(customerRepository.findAll()).thenReturn(expected);
        List<Customer> actual = customerService.getAll();

        assertThat(actual).containsExactly(listContents);
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getById_HappyFlow() {
        Customer expected = getCustomer();
        when(customerRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(expected));
        Customer actual = customerService.getById(MedewerkerTestData.TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(customerRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void getById_NotFound_throws() {
        when(customerRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.getById(MedewerkerTestData.TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND);
        verify(customerRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void createCustomer_happyFlow() {
        Customer expected = getCustomer();
        CustomerDto startData = getCustomerDto();
        when(customerRepository.existsByName(startData.getName())).thenReturn(false);
        when(customerMapper.toEntity(startData)).thenReturn(expected);
        when(customerRepository.save(expected)).thenReturn(expected);

        Customer actual = customerService.createCustomer(startData);
        assertThat(actual).isEqualTo(expected);
        verify(customerRepository, times(1)).existsByName(startData.getName());
        verify(customerRepository, times(1)).save(expected);
    }

    @Test
    void createCustomer_alreadyExists_throwsException() {
        Customer expected = getCustomer();
        CustomerDto startData = getCustomerDto();
        when(customerRepository.existsByName(startData.getName())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.createCustomer(startData));
        assertThat(exception.getMessage()).isEqualTo(CUSTOMER_ALREADY_EXISTS);
        verify(customerRepository, times(1)).existsByName(startData.getName());
        verify(customerRepository, times(0)).save(expected);
    }

    @Test
    void updateCustomer_HappyFlow() {
        Customer expected = getUpdatedNameCustomer();
        CustomerDto updateInfo = getCustomerNameUpdatedDto();
        Customer startData = getCustomer();

        when(customerRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(true);
        when(customerRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(startData));
        when(customerMapper.updateEntity(startData, updateInfo)).thenReturn(expected);
        when(customerRepository.save(expected)).thenReturn(expected);

        Customer actual = customerService.updateCustomer(MedewerkerTestData.TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(customerRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(customerRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
        verify(customerMapper, times(1)).updateEntity(startData, updateInfo);
        verify(customerRepository, times(1)).save(expected);
    }

    @Test
    void updateCustomer_NotFound_throws() {
        Customer expected = getUpdatedNameCustomer();
        CustomerDto updateInfo = getCustomerNameUpdatedDto();
        Customer startData = getCustomer();

        when(customerRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(MedewerkerTestData.TEST_ID, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND);

        verify(customerRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(customerRepository, times(0)).findById(MedewerkerTestData.TEST_ID);
        verify(customerMapper, times(0)).updateEntity(startData, updateInfo);
        verify(customerRepository, times(0)).save(expected);
    }

    @Test
    void deleteCustomer_happyFlow() {
        Customer expected = getCustomer();
        when(customerRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(true);
        when(customerRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(expected));

        Customer actual = customerService.deleteCustomer(MedewerkerTestData.TEST_ID);

        assertThat(actual).isEqualTo(expected);

        verify(customerRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(customerRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
        verify(customerRepository, times(1)).deleteById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void deleteCustomer_notFound_throws() {
        when(customerRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> customerService.deleteCustomer(MedewerkerTestData.TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(CUSTOMER_NOT_FOUND);

        verify(customerRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(customerRepository, times(0)).findById(MedewerkerTestData.TEST_ID);
        verify(customerRepository, times(0)).deleteById(MedewerkerTestData.TEST_ID);
    }

}