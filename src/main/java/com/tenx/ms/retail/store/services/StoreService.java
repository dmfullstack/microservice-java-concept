package com.tenx.ms.retail.store.services;

import com.tenx.ms.retail.common.util.EntityConverter;
import com.tenx.ms.retail.store.domain.StoreEntity;
import com.tenx.ms.retail.store.repositories.StoreRepository;
import com.tenx.ms.retail.store.rest.dto.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class StoreService {

    private static final EntityConverter<Store, StoreEntity> CONVERTER = new EntityConverter<>(Store.class, StoreEntity.class);

    @Autowired
    private StoreRepository repository;


    @Transactional
    public long create(Store store) {
        return this.repository.save(CONVERTER.toT2(store)).getStoreId();
    }

    public Optional<Store> getById(long storeId) {
        return this.repository.findByStoreId(storeId).map(CONVERTER::toT1);
    }

    public List<Store> getAll() {
        return this.repository.findAll().stream().map(CONVERTER::toT1).collect(Collectors.toList());
    }
}
