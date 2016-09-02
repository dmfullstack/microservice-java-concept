package com.tenx.ms.retail.product.service;

import com.tenx.ms.retail.common.util.EntityConverter;
import com.tenx.ms.retail.product.domain.ProductEntity;
import com.tenx.ms.retail.product.repository.ProductRepository;
import com.tenx.ms.retail.product.rest.dto.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProductService {

    private final static EntityConverter<Product, ProductEntity> CONVERTER = new EntityConverter<>(Product.class, ProductEntity.class);

    @Autowired
    private ProductRepository repository;

    @Transactional
    public Long create(Product product) {
        return this.repository.save(CONVERTER.toT2(product)).getProductId();
    }

    public Optional<Product> getByStoreIdAndProductId(long storeId, long productId) {
        return this.repository.findByStoreIdAndProductId(storeId, productId).map(CONVERTER::toT1);
    }

    public List<Product> getAllByStoreId(long storeId) {
        return this.repository.findAllByStoreId(storeId).stream().map(CONVERTER::toT1).collect(Collectors.toList());
    }
}
