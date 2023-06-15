package be.zetta.logisticsdesktop.domain.customer.repository;

import be.zetta.logisticsdesktop.domain.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByName(String name);

    Optional<Customer> getCustomersByCustomerId(String personId);
}
