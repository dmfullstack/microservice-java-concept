package com.tenx.ms.retail.order.service;

import com.tenx.ms.retail.order.domain.OrderEntity;
import com.tenx.ms.retail.order.repository.OrderRepository;
import com.tenx.ms.retail.order.rest.dto.Order;
import com.tenx.ms.retail.order.util.OrderConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Transactional
    public Order create(Order order) {
        OrderEntity response = this.repository.save(OrderConverter.convertToEntity(order));
        return OrderConverter.convertToDto(response);
    }

    public Optional<Order> getByStoreIdAndOrderId(long storeId, long orderId) {
        return this.repository.findByStoreIdAndOrderId(storeId, orderId).map(OrderConverter::convertToDto);
    }
}
