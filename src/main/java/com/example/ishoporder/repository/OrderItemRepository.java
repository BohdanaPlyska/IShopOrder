package com.example.ishoporder.repository;

import com.example.ishoporder.model.entity.OrderItem;
import com.example.ishoporder.model.entity.Product;
import com.example.ishoporder.model.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem,Long> {

    @Modifying
    @Transactional
    @Query(value = "select o from OrderItem o WHERE o.pinned = false and o.user.id = ?1")
    List<OrderItem> findUnpinnedUserOrderDetails(Long user);
    @Query("SELECT o FROM OrderItem o where o.product = ?1 and  o.user = ?2 and o.pinned = false")
    OrderItem findByKeyParam(Product product, User userId);

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem o set o.amount = ?2 + 1 WHERE o.id = ?1")
    Integer increaseAmount( Long id, Integer amount);

    @Modifying
    @Transactional
    @Query("UPDATE OrderItem o set o.amount = ?2 - 1  WHERE o.id = ?1")
    Integer decreaseAmount(Long id, Integer amount);


    @Modifying
    @Transactional
    @Query("SELECT o FROM OrderItem o WHERE o.user.id= ?1 AND o.pinned = false")
    List<OrderItem> getOrderItemForBasket(Long user);

}
