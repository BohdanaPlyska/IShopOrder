package com.example.ishoporder.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ishoporder.exception.EmptyOrderException;
import com.example.ishoporder.mapper.OrderMapper;
import com.example.ishoporder.model.entity.Order;
import com.example.ishoporder.model.entity.OrderItem;
import com.example.ishoporder.model.entity.Product;
import com.example.ishoporder.model.entity.User;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.model.response.OrderResponse;
import com.example.ishoporder.repository.OrderItemRepository;
import com.example.ishoporder.repository.OrderRepository;
import com.example.ishoporder.repository.UserRepository;
import com.example.ishoporder.utils.ResponseBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OrderService.class})
@ExtendWith(SpringExtension.class)
class OrderServiceTest {
    @MockBean
    private OrderItemRepository orderItemRepository;

    @MockBean
    private OrderMapper orderMapper;

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @MockBean
    private ResponseBuilder responseBuilder;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link OrderService#createOrder(Long)}
     */
    @Test
    void testCreateOrder() {
        when(orderItemRepository.findUnpinnedUserOrderDetails(Mockito.<Long>any())).thenReturn(new ArrayList<>());

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(EmptyOrderException.class, () -> orderService.createOrder(1L));
        verify(orderItemRepository).findUnpinnedUserOrderDetails(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link OrderService#createOrder(Long)}
     */
    @Test
    void testCreateOrder2() {
        when(orderItemRepository.findUnpinnedUserOrderDetails(Mockito.<Long>any()))
                .thenThrow(new EmptyOrderException("An error occurred"));

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        assertThrows(EmptyOrderException.class, () -> orderService.createOrder(1L));
        verify(orderItemRepository).findUnpinnedUserOrderDetails(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link OrderService#createOrder(Long)}
     */
    @Test
    void testCreateOrder3() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Order order = new Order();
        order.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order.setId(1L);
        order.setIsPaid(true);
        order.setOrderItems(new ArrayList<>());
        order.setPrice(BigDecimal.valueOf(1L));
        order.setUser(user);

        Product product = new Product();
        product.setAmount(10);
        product.setId(1L);
        product.setName("You don't have any items in the basket");
        product.setPrice(BigDecimal.valueOf(1L));

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(10);
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setPinned(true);
        orderItem.setPrice(BigDecimal.valueOf(1L));
        orderItem.setProduct(product);
        orderItem.setUser(user2);

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setRoles(new ArrayList<>());
        user3.setUsername("janedoe");

        Order order2 = new Order();
        order2.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order2.setId(1L);
        order2.setIsPaid(true);
        order2.setOrderItems(new ArrayList<>());
        order2.setPrice(BigDecimal.valueOf(1L));
        order2.setUser(user3);

        Product product2 = new Product();
        product2.setAmount(10);
        product2.setId(1L);
        product2.setName("Name");
        product2.setPrice(BigDecimal.valueOf(1L));

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setRoles(new ArrayList<>());
        user4.setUsername("janedoe");

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setAmount(10);
        orderItem2.setId(1L);
        orderItem2.setOrder(order2);
        orderItem2.setPinned(true);
        orderItem2.setPrice(BigDecimal.valueOf(1L));
        orderItem2.setProduct(product2);
        orderItem2.setUser(user4);
        when(orderItemRepository.save(Mockito.<OrderItem>any())).thenReturn(orderItem2);
        when(orderItemRepository.findUnpinnedUserOrderDetails(Mockito.<Long>any())).thenReturn(orderItemList);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setRoles(new ArrayList<>());
        user5.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user5);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setHandled(true);
        orderResponse.setId(1L);
        orderResponse.setPrice(10.0d);
        when(orderMapper.orderToOrderResponse(Mockito.<Order>any())).thenReturn(orderResponse);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setRoles(new ArrayList<>());
        user6.setUsername("janedoe");

        Order order3 = new Order();
        order3.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order3.setId(1L);
        order3.setIsPaid(true);
        order3.setOrderItems(new ArrayList<>());
        order3.setPrice(BigDecimal.valueOf(1L));
        order3.setUser(user6);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order3);
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(responseEntity);
        assertSame(responseEntity, orderService.createOrder(1L));
        verify(orderItemRepository).save(Mockito.<OrderItem>any());
        verify(orderItemRepository).findUnpinnedUserOrderDetails(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(orderMapper).orderToOrderResponse(Mockito.<Order>any());
        verify(orderRepository).save(Mockito.<Order>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link OrderService#createOrder(Long)}
     */
    @Test
    void testCreateOrder4() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Order order = new Order();
        order.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order.setId(1L);
        order.setIsPaid(true);
        order.setOrderItems(new ArrayList<>());
        order.setPrice(BigDecimal.valueOf(1L));
        order.setUser(user);

        Product product = new Product();
        product.setAmount(10);
        product.setId(1L);
        product.setName("You don't have any items in the basket");
        product.setPrice(BigDecimal.valueOf(1L));

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        OrderItem orderItem = new OrderItem();
        orderItem.setAmount(10);
        orderItem.setId(1L);
        orderItem.setOrder(order);
        orderItem.setPinned(true);
        orderItem.setPrice(BigDecimal.valueOf(1L));
        orderItem.setProduct(product);
        orderItem.setUser(user2);

        ArrayList<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItem);

        User user3 = new User();
        user3.setEmail("jane.doe@example.org");
        user3.setId(1L);
        user3.setPassword("iloveyou");
        user3.setRoles(new ArrayList<>());
        user3.setUsername("janedoe");

        Order order2 = new Order();
        order2.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order2.setId(1L);
        order2.setIsPaid(true);
        order2.setOrderItems(new ArrayList<>());
        order2.setPrice(BigDecimal.valueOf(1L));
        order2.setUser(user3);

        Product product2 = new Product();
        product2.setAmount(10);
        product2.setId(1L);
        product2.setName("Name");
        product2.setPrice(BigDecimal.valueOf(1L));

        User user4 = new User();
        user4.setEmail("jane.doe@example.org");
        user4.setId(1L);
        user4.setPassword("iloveyou");
        user4.setRoles(new ArrayList<>());
        user4.setUsername("janedoe");

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setAmount(10);
        orderItem2.setId(1L);
        orderItem2.setOrder(order2);
        orderItem2.setPinned(true);
        orderItem2.setPrice(BigDecimal.valueOf(1L));
        orderItem2.setProduct(product2);
        orderItem2.setUser(user4);
        when(orderItemRepository.save(Mockito.<OrderItem>any())).thenReturn(orderItem2);
        when(orderItemRepository.findUnpinnedUserOrderDetails(Mockito.<Long>any())).thenReturn(orderItemList);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setRoles(new ArrayList<>());
        user5.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user5);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setHandled(true);
        orderResponse.setId(1L);
        orderResponse.setPrice(10.0d);
        when(orderMapper.orderToOrderResponse(Mockito.<Order>any())).thenReturn(orderResponse);

        User user6 = new User();
        user6.setEmail("jane.doe@example.org");
        user6.setId(1L);
        user6.setPassword("iloveyou");
        user6.setRoles(new ArrayList<>());
        user6.setUsername("janedoe");

        Order order3 = new Order();
        order3.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order3.setId(1L);
        order3.setIsPaid(true);
        order3.setOrderItems(new ArrayList<>());
        order3.setPrice(BigDecimal.valueOf(1L));
        order3.setUser(user6);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order3);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenThrow(new EmptyOrderException("An error occurred"));
        assertThrows(EmptyOrderException.class, () -> orderService.createOrder(1L));
        verify(orderItemRepository).save(Mockito.<OrderItem>any());
        verify(orderItemRepository).findUnpinnedUserOrderDetails(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(orderMapper).orderToOrderResponse(Mockito.<Order>any());
        verify(orderRepository).save(Mockito.<Order>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link OrderService#performActionEveryMinute()}
     */
    @Test
    void testPerformActionEveryMinute() {
        doNothing().when(orderRepository).deleteOldOrders();
        orderService.performActionEveryMinute();
        verify(orderRepository).deleteOldOrders();
    }

    /**
     * Method under test: {@link OrderService#performActionEveryMinute()}
     */
    @Test
    void testPerformActionEveryMinute2() {
        doThrow(new EmptyOrderException("An error occurred")).when(orderRepository).deleteOldOrders();
        assertThrows(EmptyOrderException.class, () -> orderService.performActionEveryMinute());
        verify(orderRepository).deleteOldOrders();
    }

    /**
     * Method under test: {@link OrderService#payOrder(Long)}
     */
    @Test
    void testPayOrder() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Order order = new Order();
        order.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order.setId(1L);
        order.setIsPaid(true);
        order.setOrderItems(new ArrayList<>());
        order.setPrice(BigDecimal.valueOf(1L));
        order.setUser(user);
        Optional<Order> ofResult = Optional.of(order);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        Order order2 = new Order();
        order2.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order2.setId(1L);
        order2.setIsPaid(true);
        order2.setOrderItems(new ArrayList<>());
        order2.setPrice(BigDecimal.valueOf(1L));
        order2.setUser(user2);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order2);
        when(orderRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(responseEntity);
        assertSame(responseEntity, orderService.payOrder(1L));
        verify(orderRepository).save(Mockito.<Order>any());
        verify(orderRepository).findById(Mockito.<Long>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link OrderService#payOrder(Long)}
     */
    @Test
    void testPayOrder2() {
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setId(1L);
        user.setPassword("iloveyou");
        user.setRoles(new ArrayList<>());
        user.setUsername("janedoe");

        Order order = new Order();
        order.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order.setId(1L);
        order.setIsPaid(true);
        order.setOrderItems(new ArrayList<>());
        order.setPrice(BigDecimal.valueOf(1L));
        order.setUser(user);
        Optional<Order> ofResult = Optional.of(order);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setId(1L);
        user2.setPassword("iloveyou");
        user2.setRoles(new ArrayList<>());
        user2.setUsername("janedoe");

        Order order2 = new Order();
        order2.setCreated_at(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        order2.setId(1L);
        order2.setIsPaid(true);
        order2.setOrderItems(new ArrayList<>());
        order2.setPrice(BigDecimal.valueOf(1L));
        order2.setUser(user2);
        when(orderRepository.save(Mockito.<Order>any())).thenReturn(order2);
        when(orderRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenThrow(new EmptyOrderException("An error occurred"));
        assertThrows(EmptyOrderException.class, () -> orderService.payOrder(1L));
        verify(orderRepository).save(Mockito.<Order>any());
        verify(orderRepository).findById(Mockito.<Long>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }
}

