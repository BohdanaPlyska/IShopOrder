package com.example.ishoporder.repository;

import com.example.ishoporder.model.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product,Long> {

    Optional<Product> findProductByName(String name);

    @Query(value = "select o from Product o where o.amount > 0")
    List<Product> findAllAvailable();

    Optional<Product> findById(Long id);


}
