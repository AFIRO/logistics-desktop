package be.zetta.logisticsdesktop.domain.util.repository;

import be.zetta.logisticsdesktop.domain.util.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {
    Optional<Person> getPersonByPersonId(String personId);
}
