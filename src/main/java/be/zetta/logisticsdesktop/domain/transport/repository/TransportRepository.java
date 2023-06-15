package be.zetta.logisticsdesktop.domain.transport.repository;

import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransportRepository extends JpaRepository<Transport, String> {
    boolean existsByTransportName(String transportName);
    Optional<Transport> getByTransportName(String transportName);
}
