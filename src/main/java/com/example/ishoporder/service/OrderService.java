package com.example.ishoporder.service;

import com.example.ishoporder.exception.EmptyOrderException;
import com.example.ishoporder.exception.UserNotFoundException;
import com.example.ishoporder.mapper.OrderMapper;
import com.example.ishoporder.model.entity.Order;
import com.example.ishoporder.model.entity.OrderItem;
import com.example.ishoporder.model.entity.User;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.model.response.OrderResponse;
import com.example.ishoporder.repository.OrderItemRepository;
import com.example.ishoporder.repository.OrderRepository;
import com.example.ishoporder.repository.UserRepository;
import com.example.ishoporder.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ResponseBuilder responseBuilder;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);


    public ResponseEntity<ApiResponse> createOrder(Long userId){ //put orderItems not paid orders deletion after 10 minutes of creation
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        List<OrderItem> orderItemList = orderItemRepository.findUnpinnedUserOrderDetails(userId);

        if (orderItemList.isEmpty()) {
            throw new EmptyOrderException("You don't have any items in the basket");
        }

        BigDecimal price = BigDecimal.ZERO;

        for (OrderItem item : orderItemList) {
            BigDecimal itemPrice = item.getPrice().multiply(BigDecimal.valueOf(item.getAmount()));
            price = price.add(itemPrice);
        }

        Order order = new Order();
        order.setUser(user);
        order.setPrice(price);
        order.setCreated_at(new Date());
        order.setIsPaid(false);

        orderRepository.save(order);

        for (OrderItem item : orderItemList) {
            item.setOrder(order);
            orderItemRepository.save(item);
        }

        OrderResponse orderResponse = orderMapper.orderToOrderResponse(order);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "", orderResponse);

    }

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void performActionEveryMinute() {
        logger.info("Performing some action every minute.");
        orderRepository.deleteOldOrders();
    }

    public ResponseEntity<ApiResponse> payOrder(Long orderId) { //end point that will mark clients order as paid
        Order order = orderRepository.findById(orderId).get();
        order.setIsPaid(true);
        orderRepository.save(order);
        return responseBuilder.buildResponse(HttpStatus.OK.value(), "", order);

    }

}
