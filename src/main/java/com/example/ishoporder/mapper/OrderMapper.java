package com.example.ishoporder.mapper;

import com.example.ishoporder.model.entity.Order;
import com.example.ishoporder.model.entity.Product;
import com.example.ishoporder.model.response.OrderResponse;
import com.example.ishoporder.model.response.ProductResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderResponse orderToOrderResponse(Order order);
}
