package com.tenx.ms.retail.orders.domain;


import com.tenx.ms.commons.rest.IntValueEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public enum OrderStatus implements IntValueEnum<OrderStatus> {
    ORDERED(0),
    PACKING(1),
    SHIPPED(2);

    private static final Map<OrderStatus, String> STATUS_STRING_MAP;
    private final int value;

    static {
        Map<OrderStatus, String> enumMap = new HashMap<>();

        enumMap.put(OrderStatus.ORDERED, "Ordered");
        enumMap.put(OrderStatus.PACKING, "Packing");
        enumMap.put(OrderStatus.SHIPPED, "Shipped");

        STATUS_STRING_MAP = enumMap;
    }

    OrderStatus(int value) {
        this.value = value;
    }

    /**
     * Creates a new instance of OrderStatus from String.
     * @param name The String
     * @return New instance of OrderStatus
     */
    public static OrderStatus fromString(String name) {
        if (!STATUS_STRING_MAP.containsValue(name))
            throw new IllegalArgumentException("name");

        return STATUS_STRING_MAP.entrySet()
                .stream()
                .filter(x -> x.getValue().equals(name))
                .findFirst().get().getKey();
    }

    /**
     * Creates a new instance of OrderStatus from an int value.
     * @param value The int value
     * @return New instance of OrderStatus.
     */
    public static OrderStatus fromValue(int value) {
        if (!Arrays.asList(OrderStatus.values()).stream().map(OrderStatus::convert).collect(Collectors.toList()).contains(value))
            throw new IndexOutOfBoundsException("value " + value + " is out of range");
        return OrderStatus.values()[value];
    }

    public static int convert(OrderStatus status) {
        return status.getValue();
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return STATUS_STRING_MAP.get(this);
    }
}
