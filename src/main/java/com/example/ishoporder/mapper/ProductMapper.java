package com.example.ishoporder.mapper;

import com.example.ishoporder.model.entity.Product;
import com.example.ishoporder.model.request.ProductRequest;
import com.example.ishoporder.model.response.ProductResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse ProductToProductResponse(Product product);

    List<ProductResponse> ProductListToProductResponseList(List<Product> products);

    Product ProductRequestToProduct(ProductRequest productRequest);
}
