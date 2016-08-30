package com.tenx.ms.retail.util;


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
public class SimpleEntityConverter<T1, T2> {
    private Class<T1> t1;
    private Class<T2> t2;

    /**
     *  Creates a new instance of the SimpleEntityConverter class.
     * @param c1 The class for the Generic Type 1
     * @param c2 The class for the Generic Type 2
     */
    public SimpleEntityConverter(Class<T1> c1, Class<T2> c2) {
        this.t1 = c1;
        this.t2 = c2;
    }

    /**
     * Converts an object of the Generic Type 2 into a new instance of the Generic type 1
     * @param obj Instance of Generic type 2 object
     * @return Instance of Generic type 1 object
     */
    public T1 toT1(T2 obj) {
        return this.<T1, T2>convert(obj, this.t1);
    }

    /**
     * Converts an object of the Generic Type 1 into a new instance of the Generic type 2
     * @param obj Instance of Generic type 1 object
     * @return Instance of Generic type 2 object
     */
    public T2 toT2(T1 obj) {
        return this.<T2, T1>convert(obj, this.t2);
    }

    private <Y1, Y2> Y1 convert(Y2 obj, Class<?> c) {
        if (obj == null) return null;

        final Y1 ret;

        try {
            ret = (Y1)c.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

        List<Method> methods = Arrays.asList(obj.getClass().getMethods());

        methods.stream().filter(x -> x.getName().startsWith("get")).forEach(y -> SimpleEntityConverter.apply(ret, obj, y));

        return ret;
    }

    private static void apply(Object destination, Object source, Method getMethod) {
        Method setMethod;

        try {
            String methodName = getMethod.getName().replaceFirst("get", "set");
            setMethod = destination.getClass().getMethod(methodName, getMethod.getReturnType());
            setMethod.invoke(destination, getMethod.invoke(source));
        } catch (NoSuchMethodException ignored) {
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
