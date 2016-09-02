package com.tenx.ms.retail.orders.repositories;

import com.tenx.ms.retail.orders.domain.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByStoreIdAndOrderId(long storeId, long orderId);
}
