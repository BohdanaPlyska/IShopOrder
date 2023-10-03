package com.example.ishoporder.service;

import com.example.ishoporder.exception.NotFoundException;

import com.example.ishoporder.model.entity.OrderItem;
import com.example.ishoporder.model.entity.Product;
import com.example.ishoporder.model.entity.User;
import com.example.ishoporder.model.request.AddToOrderItemRequest;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.repository.OrderItemRepository;

import com.example.ishoporder.repository.ProductRepository;
import com.example.ishoporder.repository.UserRepository;
import com.example.ishoporder.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ResponseBuilder responseBuilder;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public ResponseEntity<ApiResponse> addToOrder(AddToOrderItemRequest request){ //add to Order Items
        Optional<Product> productSearch = productRepository.findById(request.getProductId());
        if (productSearch.isEmpty()) {
            throw new NotFoundException("Product isn't found");
        }

        Optional<User> userSearch = userRepository.findById(request.getUserId());

        OrderItem processedOrderItem;
        Optional<OrderItem> existOrderItem = Optional.ofNullable(
                orderItemRepository.findByKeyParam(
                        productSearch.get(),
                        userSearch.get()
                )
        );

        if (existOrderItem.isPresent()) {
            processedOrderItem = existOrderItem.get();
            processedOrderItem.setAmount(processedOrderItem.getAmount() + 1);
            productSearch.get().setAmount(productSearch.get().getAmount() - 1);
        } else {
            processedOrderItem = new OrderItem(
                    productSearch.get(),
                    1,
                    userSearch.get(),
                    productSearch.get().getPrice()
            );
            productSearch.get().setAmount(productSearch.get().getAmount() - 1);
        }

        return responseBuilder.buildResponse(HttpStatus.OK.value(),"",orderItemRepository.save(processedOrderItem));
    }

    public ResponseEntity<ApiResponse> increaseAmount(Long orderItemId) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderItemId);
        Integer inceaseAmount = orderItemRepository.increaseAmount(orderItemId, orderItem.get().getAmount());
        return responseBuilder.buildResponse(HttpStatus.OK.value(),"",inceaseAmount);
    }

    public ResponseEntity<ApiResponse> decreaseAmount(Long orderItemId) {
        Optional<OrderItem> orderItem = orderItemRepository.findById(orderItemId);
        Integer decreaseAmount = orderItemRepository.decreaseAmount(orderItemId, orderItem.get().getAmount());
        return responseBuilder.buildResponse(HttpStatus.OK.value(),"",decreaseAmount);
    }

    public ResponseEntity<ApiResponse> getOrderItemsForBasket(Long userId){
        return responseBuilder.buildResponse(HttpStatus.OK.value(),"",orderItemRepository.getOrderItemForBasket(userId));
    }

}
