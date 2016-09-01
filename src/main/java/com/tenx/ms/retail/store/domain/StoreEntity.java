package com.tenx.ms.retail.store.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "store")
@Access(AccessType.FIELD)
public class StoreEntity {
    @Id
    @GeneratedValue
    private Long storeId;
    private String name;


    public Long getStoreId() { return this.storeId; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }
}
