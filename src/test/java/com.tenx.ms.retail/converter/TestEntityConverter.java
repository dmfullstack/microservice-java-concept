package com.tenx.ms.retail.converter;

import com.tenx.ms.commons.config.Profiles;
import com.tenx.ms.commons.tests.AbstractIntegrationTest;
import com.tenx.ms.retail.RetailServiceApp;
import com.tenx.ms.retail.common.util.EntityConverter;
import com.tenx.ms.retail.converter.entities.EntityChild;
import com.tenx.ms.retail.converter.entities.EntityNoAccessGetters;
import com.tenx.ms.retail.converter.entities.EntityNoSetters;
import com.tenx.ms.retail.converter.entities.EntityOne;
import com.tenx.ms.retail.converter.entities.EntityTwo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotEquals;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RetailServiceApp.class)
@ActiveProfiles(Profiles.TEST_NOAUTH)
public class TestEntityConverter extends AbstractIntegrationTest {

    private static final int    VALUE1 = 10;
    private static final long   VALUE2 = 20L;
    private static final String VALUE3 = "Some string";

    @Test
    public void testConvert() {
        EntityConverter<EntityOne, EntityTwo> converter = new EntityConverter<>(EntityOne.class, EntityTwo.class);

        EntityOne e1 = new EntityOne(VALUE1, VALUE2, VALUE3, true);
        EntityTwo e2 = new EntityTwo(VALUE1, VALUE2, VALUE3, true);

        EntityTwo converted1 = converter.toT2(e1);
        EntityOne converted2 = converter.toT1(e2);

        assertEquals("Value mismatch field1", e1.getSomeField1(), converted1.getSomeField1());
        assertEquals("Value mismatch field2", e1.getSomeField2(), converted1.getSomeField2());
        assertEquals("Value mismatch field3", e1.getSomeField3(), converted1.getSomeField3());
        assertEquals("Value mismatch flag",   e1.isSomeFlag(),    converted1.isSomeFlag());

        assertEquals("Value mismatch field1", e2.getSomeField1(), converted2.getSomeField1());
        assertEquals("Value mismatch field2", e2.getSomeField2(), converted2.getSomeField2());
        assertEquals("Value mismatch field3", e2.getSomeField3(), converted2.getSomeField3());
        assertEquals("Value mismatch flag",   e2.isSomeFlag(),    converted2.isSomeFlag());
    }

    @Test
    public void testFieldAccess() {
        EntityConverter<EntityOne, EntityNoSetters> converter = new EntityConverter<>(EntityOne.class, EntityNoSetters.class);

        EntityOne       e1         = new EntityOne(VALUE1, VALUE2, VALUE3, true);
        EntityNoSetters converted1 = converter.toT2(e1);

        assertEquals("Value mismatch field2", e1.getSomeField2(), converted1.getSomeField2());
        assertEquals("Value mismatch field3", e1.getSomeField3(), converted1.getSomeField3());
        assertNotEquals("Value copied without proper access", e1.getSomeField1(), converted1.getSomeField1());
        assertNotEquals("Value copied without proper access", e1.isSomeFlag(),    converted1.isSomeFlag());
    }

    @Test
    public void testDenyAccessToGetter() {
        EntityConverter<EntityOne, EntityNoAccessGetters> converter = new EntityConverter<>(EntityOne.class, EntityNoAccessGetters.class);

        EntityNoAccessGetters e1         = new EntityNoAccessGetters(VALUE1, VALUE2, VALUE3, true);
        EntityOne             converted1 = converter.toT1(e1);

        assertNotEquals("Value copied without proper access", e1.getSomeField2(), converted1.getSomeField2());
        assertNotEquals("Value copied without proper access", e1.getSomeField3(), converted1.getSomeField3());
        assertEquals("Value mismatch field2", e1.getSomeField1(), converted1.getSomeField1());
        assertEquals("Value mismatch field3", e1.isSomeFlag(),    converted1.isSomeFlag());
    }

    @Test
    public void testInheritance() {
        EntityConverter<EntityOne, EntityChild> converter = new EntityConverter<>(EntityOne.class, EntityChild.class);

        EntityOne   e1         = new EntityOne(VALUE1, VALUE2, VALUE3, true);
        EntityChild converted1 = converter.toT2(e1);

        assertEquals("Value mismatch field2", e1.getSomeField2(), converted1.getSomeField2());
        assertEquals("Value mismatch field3", e1.getSomeField3(), converted1.getSomeField3());
        assertNotEquals("Value copied without proper access", e1.getSomeField1(), converted1.getSomeField1());
        assertNotEquals("Value copied without proper access", e1.isSomeFlag(),    converted1.isSomeFlag());
    }
}
