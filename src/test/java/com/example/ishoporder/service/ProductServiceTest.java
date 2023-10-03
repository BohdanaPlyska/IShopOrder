package com.example.ishoporder.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ishoporder.exception.AlreadyExistException;
import com.example.ishoporder.mapper.ProductMapper;
import com.example.ishoporder.model.entity.Product;
import com.example.ishoporder.model.request.ProductRequest;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.model.response.ProductResponse;
import com.example.ishoporder.repository.ProductRepository;
import com.example.ishoporder.utils.ResponseBuilder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ProductService.class})
@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @MockBean
    private ProductMapper productMapper;

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @MockBean
    private ResponseBuilder responseBuilder;

    /**
     * Method under test: {@link ProductService#createProduct(ProductRequest)}
     */
    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setAmount(10);
        product.setId(1L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(1L));
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findProductByName(Mockito.<String>any())).thenReturn(ofResult);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setAmount(10);
        productRequest.setName("Name");
        productRequest.setPrice(BigDecimal.valueOf(1L));
        assertThrows(AlreadyExistException.class, () -> productService.createProduct(productRequest));
        verify(productRepository).findProductByName(Mockito.<String>any());
    }

    /**
     * Method under test: {@link ProductService#createProduct(ProductRequest)}
     */
    @Test
    void testCreateProduct2() {
        Product product = new Product();
        product.setAmount(10);
        product.setId(1L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(1L));
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product);
        Optional<Product> emptyResult = Optional.empty();
        when(productRepository.findProductByName(Mockito.<String>any())).thenReturn(emptyResult);

        Product product2 = new Product();
        product2.setAmount(10);
        product2.setId(1L);
        product2.setName("Name");
        product2.setPrice(BigDecimal.valueOf(1L));

        ProductResponse productResponse = new ProductResponse();
        productResponse.setAmount(10);
        productResponse.setName("Name");
        productResponse.setPrice(BigDecimal.valueOf(1L));
        when(productMapper.ProductRequestToProduct(Mockito.<ProductRequest>any())).thenReturn(product2);
        when(productMapper.ProductToProductResponse(Mockito.<Product>any())).thenReturn(productResponse);
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(responseEntity);

        ProductRequest productRequest = new ProductRequest();
        productRequest.setAmount(10);
        productRequest.setName("Name");
        productRequest.setPrice(BigDecimal.valueOf(1L));
        assertSame(responseEntity, productService.createProduct(productRequest));
        verify(productRepository).save(Mockito.<Product>any());
        verify(productRepository).findProductByName(Mockito.<String>any());
        verify(productMapper).ProductRequestToProduct(Mockito.<ProductRequest>any());
        verify(productMapper).ProductToProductResponse(Mockito.<Product>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link ProductService#createProduct(ProductRequest)}
     */
    @Test
    void testCreateProduct3() {
        Product product = new Product();
        product.setAmount(10);
        product.setId(1L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(1L));
        when(productRepository.save(Mockito.<Product>any())).thenReturn(product);
        Optional<Product> emptyResult = Optional.empty();
        when(productRepository.findProductByName(Mockito.<String>any())).thenReturn(emptyResult);

        Product product2 = new Product();
        product2.setAmount(10);
        product2.setId(1L);
        product2.setName("Name");
        product2.setPrice(BigDecimal.valueOf(1L));

        ProductResponse productResponse = new ProductResponse();
        productResponse.setAmount(10);
        productResponse.setName("Name");
        productResponse.setPrice(BigDecimal.valueOf(1L));
        when(productMapper.ProductRequestToProduct(Mockito.<ProductRequest>any())).thenReturn(product2);
        when(productMapper.ProductToProductResponse(Mockito.<Product>any())).thenReturn(productResponse);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenThrow(new AlreadyExistException("An error occurred"));

        ProductRequest productRequest = new ProductRequest();
        productRequest.setAmount(10);
        productRequest.setName("Name");
        productRequest.setPrice(BigDecimal.valueOf(1L));
        assertThrows(AlreadyExistException.class, () -> productService.createProduct(productRequest));
        verify(productRepository).save(Mockito.<Product>any());
        verify(productRepository).findProductByName(Mockito.<String>any());
        verify(productMapper).ProductRequestToProduct(Mockito.<ProductRequest>any());
        verify(productMapper).ProductToProductResponse(Mockito.<Product>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link ProductService#getAllAvailableProducts()}
     */
    @Test
    void testGetAllAvailableProducts() {
        when(productRepository.findAllAvailable()).thenReturn(new ArrayList<>());
        when(productMapper.ProductListToProductResponseList(Mockito.<List<Product>>any())).thenReturn(new ArrayList<>());
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(responseEntity);
        assertSame(responseEntity, productService.getAllAvailableProducts());
        verify(productRepository).findAllAvailable();
        verify(productMapper).ProductListToProductResponseList(Mockito.<List<Product>>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link ProductService#getAllAvailableProducts()}
     */
    @Test
    void testGetAllAvailableProducts2() {
        when(productRepository.findAllAvailable()).thenReturn(new ArrayList<>());
        when(productMapper.ProductListToProductResponseList(Mockito.<List<Product>>any())).thenReturn(new ArrayList<>());
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenThrow(new AlreadyExistException("An error occurred"));
        assertThrows(AlreadyExistException.class, () -> productService.getAllAvailableProducts());
        verify(productRepository).findAllAvailable();
        verify(productMapper).ProductListToProductResponseList(Mockito.<List<Product>>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link ProductService#updateProductAmount(Long, Integer)}
     */
    @Test
    void testUpdateProductAmount() {
        Product product = new Product();
        product.setAmount(10);
        product.setId(1L);
        product.setName("Name");
        product.setPrice(BigDecimal.valueOf(1L));
        Optional<Product> ofResult = Optional.of(product);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertTrue(productService.updateProductAmount(1L, 1));
        verify(productRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ProductService#updateProductAmount(Long, Integer)}
     */
    @Test
    void testUpdateProductAmount2() {
        Optional<Product> emptyResult = Optional.empty();
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        assertTrue(productService.updateProductAmount(1L, 1));
        verify(productRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link ProductService#updateProductAmount(Long, Integer)}
     */
    @Test
    void testUpdateProductAmount3() {
        when(productRepository.findById(Mockito.<Long>any())).thenThrow(new AlreadyExistException("An error occurred"));
        assertThrows(AlreadyExistException.class, () -> productService.updateProductAmount(1L, 1));
        verify(productRepository).findById(Mockito.<Long>any());
    }
}

