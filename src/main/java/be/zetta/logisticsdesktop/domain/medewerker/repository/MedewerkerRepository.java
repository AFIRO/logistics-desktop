package be.zetta.logisticsdesktop.domain.medewerker.repository;

import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedewerkerRepository extends JpaRepository<Medewerker, String> {

    boolean existsMedewerkerByPerson_Email(String email);

}
