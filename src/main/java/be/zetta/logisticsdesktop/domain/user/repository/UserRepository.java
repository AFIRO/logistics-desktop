package be.zetta.logisticsdesktop.domain.user.repository;

import be.zetta.logisticsdesktop.domain.user.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, String> {
    Optional<ApplicationUser> findByEmail(String email);

    boolean existsByEmail(String email);
}
