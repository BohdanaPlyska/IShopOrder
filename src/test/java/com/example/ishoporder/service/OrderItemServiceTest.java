package com.example.ishoporder.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ishoporder.exception.NotFoundException;
import com.example.ishoporder.model.entity.Order;
import com.example.ishoporder.model.entity.OrderItem;
import com.example.ishoporder.model.entity.Product;
import com.example.ishoporder.model.entity.User;
import com.example.ishoporder.model.request.AddToOrderItemRequest;
import com.example.ishoporder.model.response.ApiResponse;
import com.example.ishoporder.repository.OrderItemRepository;
import com.example.ishoporder.repository.ProductRepository;
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

@ContextConfiguration(classes = {OrderItemService.class})
@ExtendWith(SpringExtension.class)
class OrderItemServiceTest {
    @MockBean
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemService orderItemService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ResponseBuilder responseBuilder;

    @MockBean
    private UserRepository userRepository;

    /**
     * Method under test: {@link OrderItemService#addToOrder(AddToOrderItemRequest)}
     */
    @Test
    void testAddToOrder() {
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
        product.setName("Name");
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
        when(orderItemRepository.findByKeyParam(Mockito.<Product>any(), Mockito.<User>any())).thenReturn(orderItem);
        when(orderItemRepository.save(Mockito.<OrderItem>any())).thenReturn(orderItem2);
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(responseEntity);

        Product product3 = new Product();
        product3.setAmount(10);
        product3.setId(1L);
        product3.setName("Name");
        product3.setPrice(BigDecimal.valueOf(1L));
        Optional<Product> ofResult = Optional.of(product3);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setRoles(new ArrayList<>());
        user5.setUsername("janedoe");
        Optional<User> ofResult2 = Optional.of(user5);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertSame(responseEntity, orderItemService.addToOrder(new AddToOrderItemRequest(1L, 1L)));
        verify(orderItemRepository).findByKeyParam(Mockito.<Product>any(), Mockito.<User>any());
        verify(orderItemRepository).save(Mockito.<OrderItem>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
        verify(productRepository).findById(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link OrderItemService#addToOrder(AddToOrderItemRequest)}
     */
    @Test
    void testAddToOrder2() {
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
        product.setName("Name");
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
        when(orderItemRepository.findByKeyParam(Mockito.<Product>any(), Mockito.<User>any())).thenReturn(orderItem);
        when(orderItemRepository.save(Mockito.<OrderItem>any())).thenReturn(orderItem2);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenThrow(new NotFoundException("An error occurred"));

        Product product3 = new Product();
        product3.setAmount(10);
        product3.setId(1L);
        product3.setName("Name");
        product3.setPrice(BigDecimal.valueOf(1L));
        Optional<Product> ofResult = Optional.of(product3);
        when(productRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        User user5 = new User();
        user5.setEmail("jane.doe@example.org");
        user5.setId(1L);
        user5.setPassword("iloveyou");
        user5.setRoles(new ArrayList<>());
        user5.setUsername("janedoe");
        Optional<User> ofResult2 = Optional.of(user5);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult2);
        assertThrows(NotFoundException.class, () -> orderItemService.addToOrder(new AddToOrderItemRequest(1L, 1L)));
        verify(orderItemRepository).findByKeyParam(Mockito.<Product>any(), Mockito.<User>any());
        verify(orderItemRepository).save(Mockito.<OrderItem>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
        verify(productRepository).findById(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    /**
     * Method under test: {@link OrderItemService#increaseAmount(Long)}
     */
    @Test
    void testIncreaseAmount() {
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
        product.setName("Name");
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
        Optional<OrderItem> ofResult = Optional.of(orderItem);
        when(orderItemRepository.increaseAmount(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1);
        when(orderItemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(responseEntity);
        assertSame(responseEntity, orderItemService.increaseAmount(1L));
        verify(orderItemRepository).increaseAmount(Mockito.<Long>any(), Mockito.<Integer>any());
        verify(orderItemRepository).findById(Mockito.<Long>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link OrderItemService#increaseAmount(Long)}
     */
    @Test
    void testIncreaseAmount2() {
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
        product.setName("Name");
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
        Optional<OrderItem> ofResult = Optional.of(orderItem);
        when(orderItemRepository.increaseAmount(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1);
        when(orderItemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        assertThrows(NotFoundException.class, () -> orderItemService.increaseAmount(1L));
        verify(orderItemRepository).increaseAmount(Mockito.<Long>any(), Mockito.<Integer>any());
        verify(orderItemRepository).findById(Mockito.<Long>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link OrderItemService#decreaseAmount(Long)}
     */
    @Test
    void testDecreaseAmount() {
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
        product.setName("Name");
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
        Optional<OrderItem> ofResult = Optional.of(orderItem);
        when(orderItemRepository.decreaseAmount(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1);
        when(orderItemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(responseEntity);
        assertSame(responseEntity, orderItemService.decreaseAmount(1L));
        verify(orderItemRepository).decreaseAmount(Mockito.<Long>any(), Mockito.<Integer>any());
        verify(orderItemRepository).findById(Mockito.<Long>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link OrderItemService#decreaseAmount(Long)}
     */
    @Test
    void testDecreaseAmount2() {
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
        product.setName("Name");
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
        Optional<OrderItem> ofResult = Optional.of(orderItem);
        when(orderItemRepository.decreaseAmount(Mockito.<Long>any(), Mockito.<Integer>any())).thenReturn(1);
        when(orderItemRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        assertThrows(NotFoundException.class, () -> orderItemService.decreaseAmount(1L));
        verify(orderItemRepository).decreaseAmount(Mockito.<Long>any(), Mockito.<Integer>any());
        verify(orderItemRepository).findById(Mockito.<Long>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link OrderItemService#getOrderItemsForBasket(Long)}
     */
    @Test
    void testGetOrderItemsForBasket() {
        when(orderItemRepository.getOrderItemForBasket(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(HttpStatus.CONTINUE);
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenReturn(responseEntity);
        assertSame(responseEntity, orderItemService.getOrderItemsForBasket(1L));
        verify(orderItemRepository).getOrderItemForBasket(Mockito.<Long>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }

    /**
     * Method under test: {@link OrderItemService#getOrderItemsForBasket(Long)}
     */
    @Test
    void testGetOrderItemsForBasket2() {
        when(orderItemRepository.getOrderItemForBasket(Mockito.<Long>any())).thenReturn(new ArrayList<>());
        when(responseBuilder.buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any()))
                .thenThrow(new NotFoundException("An error occurred"));
        assertThrows(NotFoundException.class, () -> orderItemService.getOrderItemsForBasket(1L));
        verify(orderItemRepository).getOrderItemForBasket(Mockito.<Long>any());
        verify(responseBuilder).buildResponse(anyInt(), Mockito.<String>any(), Mockito.<Object>any());
    }
}

