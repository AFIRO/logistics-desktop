package be.zetta.logisticsdesktop.domain.product.controller;

import be.zetta.logisticsdesktop.domain.product.entity.Product;
import be.zetta.logisticsdesktop.domain.product.entity.dto.ProductDto;
import be.zetta.logisticsdesktop.domain.product.service.ProductService;
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
public class ProductController {
    private static final String DTO_ID_NOT_EMPTY = "Dto Id must be empty for create.";
    private static final String ID_MUST_MATCH_ID_IN_DTO = "Id must match Id in Dto.";
    private final ProductService productService;
    private final Validator validator;

    public List<Product> getAllProduct() {
        log.info("Get All Product called from Controller.");
        return productService.getAll();
    }

    public Product getById(String id) {
        log.info("Get By Id for id {} Product called from Controller.", id);
        return productService.getById(id);
    }

    public Product createProduct(ProductDto data) {
        validateDto(data);
        log.info("Create Product called from Controller.");
        if (data.getProductId() != null && !data.getProductId().isBlank()) {
            log.error(DTO_ID_NOT_EMPTY);
            throw new IllegalArgumentException(DTO_ID_NOT_EMPTY);
        }
        return productService.createProduct(data);
    }

    public Product updateProduct(String id, ProductDto data) {
        validateDto(data);
        log.info("Update for id {} Product called from Controller.", id);
        if (!id.equals(data.getProductId())) {
            log.error(ID_MUST_MATCH_ID_IN_DTO);
            throw new IllegalArgumentException(ID_MUST_MATCH_ID_IN_DTO);
        }
        return productService.updateProduct(id, data);
    }

    public Product deleteProduct(String id) {
        log.info("Delete for id {} Product called from Controller.", id);
        return productService.deleteProduct(id);
    }

    private void validateDto(ProductDto data) {
        Set<ConstraintViolation<ProductDto>> violations = validator.validate(data);
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
