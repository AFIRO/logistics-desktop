package be.zetta.logisticsdesktop.domain.transport.service;

import be.zetta.logisticsdesktop.domain.transport.entity.Transport;
import be.zetta.logisticsdesktop.domain.transport.entity.dto.TransportDto;
import be.zetta.logisticsdesktop.domain.transport.mapper.TransportMapper;
import be.zetta.logisticsdesktop.domain.transport.repository.TransportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class TransportService {
    public static final String NOT_FOUND = "Transport not found";
    public static final String ALREADY_REGISTERED = "Transport already registered with this name.";
    public static final String BLANK_NAME = "Transport name can not be blank.";

    private final TransportRepository transportRepository;
    private final TransportMapper transportMapper;

    public List<Transport> getAll() {
        log.info("Get All Transports called from Service.");
        return transportRepository.findAll();
    }

    public Transport getById(String id) {
        log.info("Get By Id for id {} Transport called from Service.", id);
        return transportRepository.findById(id).orElseThrow(() -> {
            log.error(NOT_FOUND);
            return new IllegalArgumentException(NOT_FOUND);
        });
    }

    public Transport createTransport(TransportDto dto) {
        log.info("Create Transport called from Service.");
        if (dto.getTransportName().isBlank())
            throw new IllegalArgumentException(BLANK_NAME);
        if (!transportRepository.existsByTransportName(dto.getTransportName())) {
            return transportRepository.save(transportMapper.toEntity(dto));
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(ALREADY_REGISTERED);
        }
    }

    public Transport updateTransport(String id, TransportDto dto) {
        log.info("Update for id {} Transport called from Service.", id);
        if (transportRepository.existsById(id)) {
            Transport toUpdate = transportRepository.findById(id).orElseThrow(() -> {
                log.error(NOT_FOUND);
                return new IllegalArgumentException(NOT_FOUND);
            });
            return transportRepository.save(transportMapper.updateEntity(toUpdate, dto));
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }

    public Transport deleteTransport(String id) {
        log.info("Delete for id {} Transport called from Service.", id);
        if (transportRepository.existsById(id)) {
            Transport deletedTransport = transportRepository.findById(id).orElseThrow(() -> {
                log.error(NOT_FOUND);
                return new IllegalArgumentException(NOT_FOUND);
            });
            transportRepository.deleteById(id);
            return deletedTransport;
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }
}
