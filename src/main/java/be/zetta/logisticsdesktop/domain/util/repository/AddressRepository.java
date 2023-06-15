package be.zetta.logisticsdesktop.domain.util.repository;

import be.zetta.logisticsdesktop.domain.util.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
}
