package com.tenx.ms.retail.orders.util;

import com.tenx.ms.retail.common.util.EntityConverter;
import com.tenx.ms.retail.orders.domain.OrderDetailsEntity;
import com.tenx.ms.retail.orders.domain.OrderEntity;
import com.tenx.ms.retail.orders.rest.dto.Order;
import com.tenx.ms.retail.orders.rest.dto.OrderDetails;

import java.util.ArrayList;


public final class OrderConverter {
    private static final EntityConverter<Order, OrderEntity>               ORDER_CONVERTER   = new EntityConverter<>(Order.class, OrderEntity.class);
    private static final EntityConverter<OrderDetails, OrderDetailsEntity> DETAILS_CONVERTER = new EntityConverter<>(OrderDetails.class, OrderDetailsEntity.class);

    public static OrderEntity convertToEntity(Order order) {
        OrderEntity entity = ORDER_CONVERTER.toT2(order);
        entity.setProducts(new ArrayList<>());
        order.getProducts().stream().forEach(x -> entity.getProducts().add(DETAILS_CONVERTER.toT2(x)));
        entity.getProducts().stream().forEach(x -> x.setOrder(entity));
        return entity;
    }

    public static Order convertToDto(OrderEntity order) {
        Order dto = ORDER_CONVERTER.toT1(order);
        dto.setProducts(new ArrayList<>());
        order.getProducts().stream().forEach(x -> dto.getProducts().add(DETAILS_CONVERTER.toT1(x)));
        return dto;
    }
}
