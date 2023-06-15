package be.zetta.logisticsdesktop.domain.packaging.controller;

import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.packaging.service.PackagingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Controller
@Log4j2
@RequiredArgsConstructor
public class PackagingController {
    public static final String DTO_ID_NOT_EMPTY = "Dto Id must be empty for create.";
    public static final String ID_MUST_MATCH_ID_IN_DTO = "Id must match Id in Dto.";
    private final PackagingService packagingService;
    private final Validator validator;

    public List<Packaging> getAllPackaging() {
        log.info("Get All Packaging called from Controller.");
        return packagingService.getAll();
    }

    public Packaging getById(String id) {
        log.info("Get By Id for id {} Packaging called from Controller.", id);
        return packagingService.getById(id);
    }

    public Packaging createPackaging(PackagingDto data) {
        validateDto(data);
        log.info("Create Packaging called from Controller.");
        if (data.getPackagingId() != null && !data.getPackagingId().isBlank()) {
            log.error(DTO_ID_NOT_EMPTY);
            throw new IllegalArgumentException(DTO_ID_NOT_EMPTY);
        }
        return packagingService.createPackaging(data);
    }

    public Packaging updatePackaging(String id, PackagingDto data) {
        validateDto(data);
        log.info("Update for id {} Packaging called from Controller.", id);
        if (!id.equals(data.getPackagingId())) {
            log.error(ID_MUST_MATCH_ID_IN_DTO);
            throw new IllegalArgumentException(ID_MUST_MATCH_ID_IN_DTO);
        }
        return packagingService.updatePackaging(id, data);
    }

    public Packaging deletePackaging(String id) {
        log.info("Delete for id {} Packaging called from Controller.", id);
        return packagingService.deletePackaging(id);
    }


    private void validateDto(PackagingDto data) {
        Set<ConstraintViolation<PackagingDto>> violations = validator.validate(data);
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
