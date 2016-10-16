package com.tenx.ms.retail.stock.service;

import com.tenx.ms.retail.common.util.EntityConverter;
import com.tenx.ms.retail.stock.domain.StockEntity;
import com.tenx.ms.retail.stock.repository.StockRepository;
import com.tenx.ms.retail.stock.rest.dto.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class StockService {
    private static EntityConverter<Stock, StockEntity> CONVERTER = new EntityConverter<>(Stock.class, StockEntity.class);

    @Autowired
    private StockRepository repository;

    @Transactional
    public void createOrUpdate(Stock stock) {
        this.repository.save(CONVERTER.toT2(stock));
    }

    public Optional<Stock> getByStoreIdAndProductId(long storeId, long productId) {
        return this.repository.findByStoreIdAndProductId(storeId, productId).map(CONVERTER::toT1);
    }
}
