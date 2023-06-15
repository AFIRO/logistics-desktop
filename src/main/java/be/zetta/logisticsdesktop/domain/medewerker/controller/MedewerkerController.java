package be.zetta.logisticsdesktop.domain.medewerker.controller;

import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.entity.dto.MedewerkerDto;
import be.zetta.logisticsdesktop.domain.medewerker.service.MedewerkerService;
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
public class MedewerkerController {
    private final MedewerkerService medewerkerService;
    private final Validator validator;

    public List<Medewerker> getAllMedewerkers() {
        log.info("Get All Medewerkers called from Controller.");
        return medewerkerService.getAll();
    }

    public Medewerker getById(String id) {
        log.info("Get By Id for id {} Medewerker called from Controller.", id);
        return medewerkerService.getById(id);
    }

    public Medewerker createMedewerker(MedewerkerDto data) {
        validateDto(data);
        log.info("Create Medewerker called from Controller.");
        return medewerkerService.createMedewerker(data);
    }

    public Medewerker updateMedewerker(String id, MedewerkerDto data) {
        validateDto(data);
        log.info("Update for id {} Medewerker called from Controller.", id);
        return medewerkerService.updateMedewerker(id, data);
    }

    public Medewerker deleteMedewerker(String id) {
        log.info("Delete for id {} Medewerker called from Controller.", id);
        return medewerkerService.deleteMedewerker(id);
    }


    private void validateDto(MedewerkerDto data) {
        Set<ConstraintViolation<MedewerkerDto>> violations = validator.validate(data);
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
