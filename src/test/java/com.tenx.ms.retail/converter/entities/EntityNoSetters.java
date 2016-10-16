package com.tenx.ms.retail.converter.entities;


import com.tenx.ms.retail.common.util.AllowConverterAccess;

public class EntityNoSetters {

    @AllowConverterAccess
    private long someField2;

    @AllowConverterAccess
    private String  someField3;

    private int someField1;
    private boolean someFlag;


    public EntityNoSetters() {
    }

    public EntityNoSetters(int someField1, long someField2, String someField3, boolean someFlag) {
        this.someField1 = someField1;
        this.someField2 = someField2;
        this.someField3 = someField3;
        this.someFlag   = someFlag;
    }

    public long getSomeField2() { return this.someField2; }

    public String getSomeField3() { return this.someField3; }

    public int getSomeField1() { return this.someField1; }

    public boolean isSomeFlag() { return this.someFlag; }
}
