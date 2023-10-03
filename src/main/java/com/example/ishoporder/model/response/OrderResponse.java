package com.example.ishoporder.model.response;

import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private double price;
    private boolean handled;
}
