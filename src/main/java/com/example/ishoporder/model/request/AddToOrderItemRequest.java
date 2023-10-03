package com.example.ishoporder.model.request;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class AddToOrderItemRequest {
    private Long productId;
    private Long userId;

    public AddToOrderItemRequest(Long productId, Long userId) {
        this.productId = productId;
        this.userId = userId;
    }
}
