package com.example.ishoporder.controller;

import com.example.ishoporder.model.request.ProductRequest;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @PostMapping("/product")
    public ResponseEntity<ApiResponse> create(@RequestBody @Valid ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }


    @GetMapping("get-available") // TODO: fix
    public ResponseEntity<ApiResponse> getAllAvailable(){
        return productService.getAllAvailableProducts();
    }
}
