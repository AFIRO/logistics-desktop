package be.zetta.logisticsdesktop.domain.transport.controller;

import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.transport.mapper.TransportMapper;
import be.zetta.logisticsdesktop.domain.transport.service.TransportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequiredArgsConstructor
public class TransportController {
    public static final String DTO_ID_NOT_EMPTY = "Dto Id must be empty for create.";
    public static final String ID_MUST_MATCH_ID_IN_DTO = "Id must match Id in Dto.";
    private final TransportService transportService;
    private final TransportMapper transportMapper;
    private final Validator validator;

    public List<TransportDto> getAllTransports() {
        log.info("Get All Transport called from Controller.");
        return transportService.getAll().stream()
                .map(transportMapper::toDto)
                .collect(Collectors.toList());
    }

    public TransportDto getById(String id) {
        log.info("Get By Id for id {} Transport called from Controller.", id);
        return transportMapper.toDto(transportService.getById(id));
    }

    public TransportDto createTransport(TransportDto data) {
        validateDto(data);
        log.info("Create Transport called from Controller.");
        if (data.getTransportId() != null && !data.getTransportId().isBlank()) {
            log.error(DTO_ID_NOT_EMPTY);
            throw new IllegalArgumentException(DTO_ID_NOT_EMPTY);
        }
        return transportMapper.toDto(transportService.createTransport(data));
    }

    public TransportDto updateTransport(String id, TransportDto data) {
        validateDto(data);
        log.info("Update for id {} Transport called from Controller.", id);
        if (!id.equals(data.getTransportId())) {
            log.error(ID_MUST_MATCH_ID_IN_DTO);
            throw new IllegalArgumentException(ID_MUST_MATCH_ID_IN_DTO);
        }

        return transportMapper.toDto(transportService.updateTransport(id, data));
    }


    public TransportDto deleteTransport(String id) {
        log.info("Delete for id {} Transport called from Controller.", id);
        return transportMapper.toDto(transportService.deleteTransport(id));
    }

    private void validateDto(TransportDto data) {
        Set<ConstraintViolation<TransportDto>> violations = validator.validate(data);
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
