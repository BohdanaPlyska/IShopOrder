package com.example.ishoporder.controller;


import com.example.ishoporder.model.entity.User;
import com.example.ishoporder.model.request.AddToOrderItemRequest;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.service.OrderItemService;
import com.example.ishoporder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping(value = "order-items")
@RequiredArgsConstructor
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final UserService userService;

    @PostMapping("/basket/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> addOrderItemsToBasket(@PathVariable Long productId, Authentication authentication) {
        Optional<User> user = userService.findByUsername(authentication.getName());
        AddToOrderItemRequest orderItem = new AddToOrderItemRequest(productId,user.get().getId());
        return orderItemService.addToOrder(orderItem);
    }

    @GetMapping("/basket")
    public ResponseEntity<ApiResponse> getBasketItems(Authentication authentication) {
        Optional<User> user = userService.findByUsername(authentication.getName());
        return orderItemService.getOrderItemsForBasket(user.get().getId());
    }

    @PostMapping("/increase-amount/{orderItemId}")
    public  ResponseEntity<ApiResponse> increaseItemAmount(@PathVariable Long orderItemId){
        return orderItemService.increaseAmount(orderItemId);
    }

    @PostMapping("/decrease-amount/{orderItemId}")
    public  ResponseEntity<ApiResponse> decreaseItemAmount(@PathVariable Long orderItemId){
        return orderItemService.decreaseAmount(orderItemId);
    }



}
