package com.tenx.ms.retail.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;


/**
 * Utility class to automatically convert entities with similar structures (IE. Database Entities and Data Transport Objects)
 * This class uses reflection to obtain a list of getters in the source object and attempts to find setters with the same name
 * and same value type in the target object to invoke them. Allows 2 way conversion.
 * @param <T1> The Generic Type 2
 * @param <T2> The Generic Type 1
 */
@SuppressWarnings("unchecked")
public class EntityConverter<T1, T2> {
    private Class<T1> t1;
    private Class<T2> t2;

    /**
     *  Creates a new instance of the EntityConverter class.
     * @param c1 The class for the Generic Type 1
     * @param c2 The class for the Generic Type 2
     */
    public EntityConverter(Class<T1> c1, Class<T2> c2) {
        this.t1 = c1;
        this.t2 = c2;
    }

    /**
     * Converts an object of the Generic Type 2 into a new instance of the Generic type 1
     * @param obj Instance of Generic type 2 object
     * @return Instance of Generic type 1 object
     */
    public T1 toT1(T2 obj) {
        return EntityConverter.<T1, T2>convert(obj, this.t1);
    }

    /**
     * Converts an object of the Generic Type 1 into a new instance of the Generic type 2
     * @param obj Instance of Generic type 1 object
     * @return Instance of Generic type 2 object
     */
    public T2 toT2(T1 obj) {
        return EntityConverter.<T2, T1>convert(obj, this.t2);
    }

    /**
     * Static method to convert from a Genetic type to another.
     * @param obj The instance of the object to convert
     * @param c The class of the target object to create
     * @param <Y1> Generic type for destination object
     * @param <Y2> Generic type for source object
     * @return Instance of the specified type with the values copied from the source object.
     */
    public static <Y1, Y2> Y1 convert(Y2 obj, Class<?> c) {
        if (obj == null) return null;

        final Y1 ret;

            try {
                ret = (Y1) c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }

        List<Method> methods = Arrays.asList(obj.getClass().getMethods());

        methods.stream().filter(x -> x.getName().startsWith("get")).forEach(y -> EntityConverter.apply(ret, obj, y));

        return ret;
    }

    private static void apply(Object destination, Object source, Method getter) {
        if (getter.isAnnotationPresent(DenyConverterAccess.class))
            return;

        String getterName = getter.getName();
        Object value = null;

        /*
         *   Get the value from the source. If the retrieval of the value is not possible,
         *   the rest of the process is skipped.
         */
        try {
            value = getter.invoke(source);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        if (value == null) return;

        /*
         *  Attempt to get a getter matching the field name of the setter and assigned the value.
         */
        try {
            String methodName = getterName.replaceFirst("get", "set");
            Method setter = destination.getClass().getMethod(methodName, getter.getReturnType());

            setter.invoke(destination, value);
            return;

        } catch (NoSuchMethodException ignored) {
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        /*
         *  If obtaining a setter or invoking the setter fails, an attempt to retrieve the Field
         *  will be done. If the given field is annotated with @AllowConverterAccess, the assignment
         *  will be made, otherwise it will be skipped.
         */
        String fieldName = getterName.replaceFirst("get", "");
        fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);

        Class<?> current = destination.getClass();

        while (current != Object.class) {
            try {
                Field f = destination.getClass().getDeclaredField(fieldName);

                if (f.isAnnotationPresent(AllowConverterAccess.class)) {
                    boolean wasAccessible = f.isAccessible();
                    f.setAccessible(true);
                    f.set(destination, value);
                    f.setAccessible(wasAccessible);
                    break;
                }
            } catch (NoSuchFieldException ignored) {
            } catch (IllegalAccessException e){
                e.printStackTrace();
            }

            current = current.getSuperclass();
        }
    }
}
