package com.example.ishoporder.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponse {

    private String name;

    private BigDecimal price;

    private Integer amount;

}
