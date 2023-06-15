package be.zetta.logisticsdesktop.domain.medewerker.service;

import be.zetta.logisticsdesktop.domain.medewerker.entity.Medewerker;
import be.zetta.logisticsdesktop.domain.medewerker.entity.dto.MedewerkerDto;
import be.zetta.logisticsdesktop.domain.medewerker.mapper.MedewerkerMapper;
import be.zetta.logisticsdesktop.domain.medewerker.repository.MedewerkerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MedewerkerService {
    public static final String MEDEWERKER_NOT_FOUND = "Medewerker not found";
    public static final String ALREADY_REGISTERED_WITH_THIS_EMAIL = "Medewerker already registered with this mail.";
    private final MedewerkerRepository medewerkerRepository;
    private final MedewerkerMapper medewerkerMapper;

    public List<Medewerker> getAll() {
        log.info("Get All Medewerkers called from Service.");
        return medewerkerRepository.findAll();
    }

    public Medewerker getById(String id) {
        log.info("Get By Id for id {} Medewerker called from Service.", id);
        return medewerkerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Medewerker with Id {} not found in database", id);
                    return new IllegalArgumentException(MEDEWERKER_NOT_FOUND);
                });
    }

    public Medewerker createMedewerker(MedewerkerDto dto) {
        log.info("Create Medewerker called from Service.");
        if (!medewerkerRepository.existsMedewerkerByPerson_Email(dto.getPerson().getEmail())) {
            return medewerkerRepository.save(medewerkerMapper.toEntity(dto));
        } else {
            throw new IllegalArgumentException(ALREADY_REGISTERED_WITH_THIS_EMAIL);
        }
    }

    public Medewerker updateMedewerker(String id, MedewerkerDto dto) {
        log.info("Update for id {} Medewerker called from Service.", id);
        if (medewerkerRepository.existsById(id)) {
            Medewerker toUpdate = medewerkerRepository.findById(id).orElseThrow(() -> {
                log.error("Medewerker with Id {} not found in database", id);
                return new IllegalArgumentException(MEDEWERKER_NOT_FOUND);
            });
            return medewerkerRepository.save(medewerkerMapper.updateEntity(toUpdate, dto));
        } else {
            log.error("Medewerker with Id {} not found in database", id);
            throw new IllegalArgumentException(MEDEWERKER_NOT_FOUND);
        }
    }

    public Medewerker deleteMedewerker(String id) {
        log.info("Delete for id {} Medewerker called from Service.", id);
        if (medewerkerRepository.existsById(id)) {
            Medewerker deletedMedewerker = medewerkerRepository.findById(id).orElseThrow(() -> {
                log.error("Medewerker with Id {} not found in database", id);
                return new IllegalArgumentException(MEDEWERKER_NOT_FOUND);
            });
            medewerkerRepository.deleteById(id);
            return deletedMedewerker;
        } else {
            log.error("Medewerker with Id {} not found in database", id);
            throw new IllegalArgumentException(MEDEWERKER_NOT_FOUND);
        }
    }
}
