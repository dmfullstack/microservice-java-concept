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
    private long storeId;
    private String name;


    public long getStoreId() { return this.storeId; }

    public void setStoreId(long storeId) { this.storeId = storeId; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }
}
