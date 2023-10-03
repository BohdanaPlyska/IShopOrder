package com.example.ishoporder.service;

import com.example.ishoporder.exception.AlreadyExistException;
import com.example.ishoporder.mapper.ProductMapper;
import com.example.ishoporder.model.entity.Product;
import com.example.ishoporder.model.request.ProductRequest;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.model.response.ProductResponse;
import com.example.ishoporder.repository.ProductRepository;
import com.example.ishoporder.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ResponseBuilder responseBuilder;

    public ResponseEntity<ApiResponse> createProduct(ProductRequest productRequest)  { //create items/phones
       isProductAlreadyExists(productRequest.getName());
       Product validProduct = productMapper.ProductRequestToProduct(productRequest);
       Product product = productRepository.save(validProduct);
        ProductResponse response = productMapper.ProductToProductResponse(product);
        return responseBuilder.buildResponse(HttpStatus.OK.value(),"",response);
    }

    private void isProductAlreadyExists (String name){
        Optional<Product> product = productRepository.findProductByName(name);
        if(product.isPresent()) {
            throw new AlreadyExistException("Product already exist!");
        }
    }
    public ResponseEntity<ApiResponse>  getAllAvailableProducts() { //make with filter, allAvailable/their prices/quantities
        List<Product> productList = productRepository.findAllAvailable();
        List<ProductResponse> productResponses = productMapper.ProductListToProductResponseList(productList);
        return responseBuilder.buildResponse(HttpStatus.OK.value(),"",productResponses);

    }

    public boolean updateProductAmount(Long productId, Integer productAmount) {
        Optional<Product> product = productRepository.findById(productId);
        product.ifPresent(value ->value.setAmount(value.getAmount() - productAmount));
        return true;
    }


}
