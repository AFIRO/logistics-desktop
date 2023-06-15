package be.zetta.logisticsdesktop.domain.packaging.repository;

import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackagingRepository extends JpaRepository<Packaging, String> {
    boolean existsByPackagingName(String packagingName);
    Optional<Packaging> getByPackagingName(String packagingName);
}
