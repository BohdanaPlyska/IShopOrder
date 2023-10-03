package com.example.ishoporder.controller;

import com.example.ishoporder.model.entity.User;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.service.OrderService;
import com.example.ishoporder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    @PostMapping("order")
    public ResponseEntity<ApiResponse> createOrder(Authentication authentication) {
        User user = userService.findByUsername(authentication.getName()).get();
        return orderService.createOrder(user.getId());
    }

    @PatchMapping("/pay/{id}")
    public ResponseEntity<ApiResponse> payOrder(@PathVariable Long id) {
        return orderService.payOrder(id);
    }
}
