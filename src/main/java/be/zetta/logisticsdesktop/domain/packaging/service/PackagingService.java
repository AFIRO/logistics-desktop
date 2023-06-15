package be.zetta.logisticsdesktop.domain.packaging.service;

import be.zetta.logisticsdesktop.domain.packaging.entity.Packaging;
import be.zetta.logisticsdesktop.domain.packaging.entity.dto.PackagingDto;
import be.zetta.logisticsdesktop.domain.packaging.mapper.PackagingMapper;
import be.zetta.logisticsdesktop.domain.packaging.repository.PackagingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PackagingService {
    public static final String NOT_FOUND = "Packaging not found";
    public static final String ALREADY_REGISTERED = "Packaging already registered with this name.";
    private final PackagingRepository packagingRepository;
    private final PackagingMapper packagingMapper;

    public List<Packaging> getAll() {
        log.info("Get All Packaging called from Service.");
        return packagingRepository.findAll();
    }

    public Packaging getById(String id) {
        log.info("Get By Id for id {} Packaging called from Service.", id);
        return packagingRepository.findById(id).orElseThrow(() -> {
            log.error(NOT_FOUND);
            return new IllegalArgumentException(NOT_FOUND);
        });
    }

    public Packaging createPackaging(PackagingDto dto) {
        log.info("Create Packaging called from Service.");
        if (!packagingRepository.existsByPackagingName(dto.getPackagingName())) {
            return packagingRepository.save(packagingMapper.toEntity(dto));
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(ALREADY_REGISTERED);
        }
    }

    public Packaging updatePackaging(String id, PackagingDto dto) {
        log.info("Update for id {} Packaging called from Service.", id);
        if (packagingRepository.existsById(id)) {
            Packaging toUpdate = packagingRepository.findById(id).orElseThrow(() -> {
                log.error(NOT_FOUND);
                return new IllegalArgumentException(NOT_FOUND);
            });
            return packagingRepository.save(packagingMapper.updateEntity(toUpdate, dto));
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }

    public Packaging deletePackaging(String id) {
        log.info("Delete for id {} Packaging called from Service.", id);
        if (packagingRepository.existsById(id)) {
            Packaging deletedPackaging = packagingRepository.findById(id).orElseThrow(() -> {
                log.error(NOT_FOUND);
                return new IllegalArgumentException(NOT_FOUND);
            });
            packagingRepository.deleteById(id);
            return deletedPackaging;
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }
}
