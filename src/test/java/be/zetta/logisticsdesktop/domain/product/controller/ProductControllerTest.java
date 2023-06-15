package be.zetta.logisticsdesktop.domain.product.controller;


import be.zetta.logisticsdesktop.domain.product.entity.Product;
import be.zetta.logisticsdesktop.domain.product.entity.dto.ProductDto;
import be.zetta.logisticsdesktop.domain.product.service.ProductService;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;

import static be.zetta.logisticsdesktop.domain.product.ProductTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    private ProductService productService;
    @Mock
    private Validator validator;
    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProduct() {
        Product listContents = getProduct();
        List<Product> expected = List.of(listContents);
        when(productService.getAll()).thenReturn(expected);
        List<Product> actual = productController.getAllProduct();

        assertThat(actual).containsExactly(listContents);
        verify(productService, times(1)).getAll();
    }

    @Test
    void getById() {
        Product expected = getProduct();
        when(productService.getById(MedewerkerTestData.TEST_ID)).thenReturn(expected);
        Product actual = productController.getById(MedewerkerTestData.TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(productService, times(1)).getById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void createProduct_happyflow() {
        Product expected = getProduct();
        ProductDto startData = getProductDtoNoId();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(productService.createProduct(startData)).thenReturn(expected);

        Product actual = productController.createProduct(startData);
        assertThat(actual).isEqualTo(expected);
        verify(productService, times(1)).createProduct(startData);
    }

    @Test
    void createProduct_IdNotEmpty_throws() {
        ProductDto startData = getProductDto();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productController.createProduct(startData));
        assertThat(exception.getMessage()).isEqualTo(DTO_ID_NOT_EMPTY);
        verify(productService, times(0)).createProduct(startData);
    }

    @Test
    void updateProduct_happyflow() {
        Product expected = getProductUpdated();
        ProductDto updateInfo = getProductDtoUpdated();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        when(productService.updateProduct(PRODUCT_ID, updateInfo)).thenReturn(expected);
        Product actual = productController.updateProduct(PRODUCT_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(productService, times(1)).updateProduct(PRODUCT_ID, updateInfo);
    }

    @Test
    void updateProduct_IdDoesNotMatch_throws() {
        ProductDto updateInfo = getProductDtoUpdated();
        when(validator.validate(any(),any() )).thenReturn(new HashSet<>());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productController.updateProduct("WRONG", updateInfo));
        assertThat(exception.getMessage()).isEqualTo(ID_MUST_MATCH_ID_IN_DTO);
        verify(productService, times(0)).updateProduct(MedewerkerTestData.TEST_ID, updateInfo);
    }

    @Test
    void deleteProduct() {
        Product expected = getProduct();
        when(productService.deleteProduct(MedewerkerTestData.TEST_ID)).thenReturn(expected);
        Product actual = productController.deleteProduct(MedewerkerTestData.TEST_ID);

        assertThat(actual).isEqualTo(expected);
        verify(productService, times(1)).deleteProduct(MedewerkerTestData.TEST_ID);
    }

}