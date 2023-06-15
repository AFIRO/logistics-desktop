package be.zetta.logisticsdesktop.domain.product.service;

import be.zetta.logisticsdesktop.domain.product.entity.Product;
import be.zetta.logisticsdesktop.domain.product.entity.dto.ProductDto;
import be.zetta.logisticsdesktop.domain.product.mapper.ProductMapper;
import be.zetta.logisticsdesktop.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductService {
    public static final String NOT_FOUND = "Product not found";
    public static final String ALREADY_REGISTERED = "Product already registered with this name.";

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<Product> getAll() {
        log.info("Get All Product called from Service.");
        return productRepository.findAll();
    }

    public Product getById(String id) {
        log.info("Get By Id for id {} Product called from Service.", id);
        return productRepository.findById(id).orElseThrow(() -> {
            log.error(NOT_FOUND);
            return new IllegalArgumentException(NOT_FOUND);
        });
    }

    public Product createProduct(ProductDto dto) {
        log.info("Create Product called from Service.");
        if (!productRepository.existsByName(dto.getName())) {
            return productRepository.save(productMapper.toEntity(dto));
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(ALREADY_REGISTERED);
        }
    }

    public Product updateProduct(String id, ProductDto dto) {
        log.info("Update for id {} Product called from Service.", id);
        if (productRepository.existsById(id)) {
            Product toUpdate = productRepository.findById(id).orElseThrow(() -> {
                log.error(NOT_FOUND);
                return new IllegalArgumentException(NOT_FOUND);
            });
            return productRepository.save(productMapper.updateEntity(toUpdate, dto));
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }

    public Product deleteProduct(String id) {
        log.info("Delete for id {} Product called from Service.", id);
        if (productRepository.existsById(id)) {
            Product deletedProduct = productRepository.findById(id).orElseThrow(() -> {
                log.error(NOT_FOUND);
                return new IllegalArgumentException(NOT_FOUND);
            });
            productRepository.deleteById(id);
            return deletedProduct;
        } else {
            log.error(NOT_FOUND);
            throw new IllegalArgumentException(NOT_FOUND);
        }
    }
}
