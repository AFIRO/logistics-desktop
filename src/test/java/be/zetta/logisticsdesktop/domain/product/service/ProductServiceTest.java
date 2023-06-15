package be.zetta.logisticsdesktop.domain.product.service;


import be.zetta.logisticsdesktop.domain.product.entity.Product;
import be.zetta.logisticsdesktop.domain.product.entity.dto.ProductDto;
import be.zetta.logisticsdesktop.domain.product.mapper.ProductMapper;
import be.zetta.logisticsdesktop.domain.product.repository.ProductRepository;
import be.zetta.logisticsdesktop.domain.medewerker.MedewerkerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static be.zetta.logisticsdesktop.domain.product.ProductTestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductMapper productMapper;
    @InjectMocks
    private ProductService productService;

    @Test
    void getAll_happyFlow() {
        Product listContents = getProduct();
        List<Product> expected = List.of(listContents);
        when(productRepository.findAll()).thenReturn(expected);
        List<Product> actual = productService.getAll();

        assertThat(actual).containsExactly(listContents);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getById_HappyFlow() {
        Product expected = getProduct();
        when(productRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(expected));
        Product actual = productService.getById(MedewerkerTestData.TEST_ID);
        assertThat(actual).isEqualTo(expected);
        verify(productRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void getById_NotFound_throws() {
        when(productRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.getById(MedewerkerTestData.TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);
        verify(productRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void createProduct_happyFlow() {
        Product expected = getProduct();
        ProductDto startData = getProductDto();
        when(productRepository.existsByName(startData.getName())).thenReturn(false);
        when(productMapper.toEntity(startData)).thenReturn(expected);
        when(productRepository.save(expected)).thenReturn(expected);

        Product actual = productService.createProduct(startData);
        assertThat(actual).isEqualTo(expected);
        verify(productRepository, times(1)).existsByName(startData.getName());
        verify(productRepository, times(1)).save(expected);
    }

    @Test
    void createProduct_alreadyExists_throwsException() {
        Product expected = getProduct();
        ProductDto startData = getProductDto();
        when(productRepository.existsByName(startData.getName())).thenReturn(true);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.createProduct(startData));
        assertThat(exception.getMessage()).isEqualTo(ALREADY_REGISTERED);
        verify(productRepository, times(1)).existsByName(startData.getName());
        verify(productRepository, times(0)).save(expected);
    }

    @Test
    void updateProduct_HappyFlow() {
        Product expected = getProductUpdated();
        ProductDto updateInfo = getProductDtoUpdated();
        Product startData = getProduct();

        when(productRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(true);
        when(productRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(startData));
        when(productMapper.updateEntity(startData, updateInfo)).thenReturn(expected);
        when(productRepository.save(expected)).thenReturn(expected);

        Product actual = productService.updateProduct(MedewerkerTestData.TEST_ID, updateInfo);

        assertThat(actual).isEqualTo(expected);
        verify(productRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(productRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
        verify(productMapper, times(1)).updateEntity(startData, updateInfo);
        verify(productRepository, times(1)).save(expected);
    }

    @Test
    void updateProduct_NotFound_throws() {
        Product expected = getProductUpdated();
        ProductDto updateInfo = getProductDtoUpdated();
        Product startData = getProduct();

        when(productRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(MedewerkerTestData.TEST_ID, updateInfo));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);

        verify(productRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(productRepository, times(0)).findById(MedewerkerTestData.TEST_ID);
        verify(productMapper, times(0)).updateEntity(startData, updateInfo);
        verify(productRepository, times(0)).save(expected);
    }

    @Test
    void deleteProduct_happyFlow() {
        Product expected = getProduct();
        when(productRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(true);
        when(productRepository.findById(MedewerkerTestData.TEST_ID)).thenReturn(Optional.ofNullable(expected));

        Product actual = productService.deleteProduct(MedewerkerTestData.TEST_ID);

        assertThat(actual).isEqualTo(expected);

        verify(productRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(productRepository, times(1)).findById(MedewerkerTestData.TEST_ID);
        verify(productRepository, times(1)).deleteById(MedewerkerTestData.TEST_ID);
    }

    @Test
    void deleteProduct_notFound_throws() {
        when(productRepository.existsById(MedewerkerTestData.TEST_ID)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> productService.deleteProduct(MedewerkerTestData.TEST_ID));
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND);

        verify(productRepository, times(1)).existsById(MedewerkerTestData.TEST_ID);
        verify(productRepository, times(0)).findById(MedewerkerTestData.TEST_ID);
        verify(productRepository, times(0)).deleteById(MedewerkerTestData.TEST_ID);
    }

}