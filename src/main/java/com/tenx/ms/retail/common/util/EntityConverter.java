package com.tenx.ms.retail.common.util;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.LocalVariableNode;
import jdk.internal.org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


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

        Y1 newInstance = null;

        List<Constructor> constructors = Arrays.asList(c.getConstructors());
        List<Method>      methods      = Arrays.asList(obj.getClass().getMethods());

        constructors.sort((x, y) -> x.getParameterCount() < y.getParameterCount() ? 1 : -1);

        final List<Method> usedMethods = new ArrayList<>();

        for (Constructor constr : constructors) {
            try {
                List<Method> cInfo  = EntityConverter.getConstructorInfo(constr, methods);

                if (cInfo == null) continue;

                List<Object> args = EntityConverter.getConstructorArgs(cInfo, obj);

                newInstance = (Y1) constr.newInstance(args.toArray());
                usedMethods.addAll(cInfo);
                break;

            } catch (IOException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (newInstance == null)
            return null;

        final Y1 ret = newInstance;

        methods.stream().filter(x -> x.getName().startsWith("get") && !usedMethods.contains(x)).forEach(y -> EntityConverter.apply(ret, obj, y));

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

    /**
     * Gets the getters required for the constructor in the proper order. If the proper setter is not found, the constructor is
     * considered invalid and return will be null.
     * @param c The constructor to analyze
     * @param methods The list of methods in the class.
     * @return The list of getter methods required to invoke the constructor if the constructor is considered valid. Returns null if the
     * constructor is not valid.
     */
    private static List<Method> getConstructorInfo(Constructor c, List<Method> methods) throws IOException {
        List<String> params  = EntityConverter.getParameterNames(c);
        List<Method> ret = new ArrayList<>();

        if (params == null)
            return null;

        for (String p : params) {

            Optional<Method> m = methods
                    .stream()
                    .filter(x -> Objects.equals(x.getName().replace("get", "").toLowerCase(), p.toLowerCase()))
                    .findFirst();

            if (m.isPresent()) {
                ret.add(m.get());
                continue;
            }

            return null;
        }

        return ret;
    }

    /**
     * Get the constructor arguments using the getters list and the source object.
     * @param cInfo The getters list of the source object in the right order.
     * @param source The source object.
     * @return A list of arguments to be used for the given constructor.
     * @throws InvocationTargetException if the underlying method throws an exception.
     * @throws IllegalAccessException if this {@code Method} object is enforcing Java language access control and the underlying method is inaccessible.
     */
    private static List<Object> getConstructorArgs(List<Method> cInfo, Object source) throws InvocationTargetException, IllegalAccessException {
        List<Object> args = new ArrayList<>();
        for (Method m : cInfo) { args.add(m.invoke(source)); }
        return args;
    }

    /**
     * The the list of parameter names from the given constructor.
     * @param constructor The constructor.
     * @return A list of parameter names in the order expected by the constructor.
     * @throws IOException
     */
    private static List<String> getParameterNames(Constructor<?> constructor) throws IOException {
        Class<?>    declaringClass        = constructor.getDeclaringClass();
        ClassLoader declaringClassLoader  = declaringClass.getClassLoader();
        Type        declaringType         = Type.getType(declaringClass);
        String      constructorDescriptor = Type.getConstructorDescriptor(constructor);
        String      url                   = declaringType.getInternalName() + ".class";
        InputStream classFileInputStream  = declaringClassLoader.getResourceAsStream(url);

        if (classFileInputStream == null) {
            throw new IllegalArgumentException("The constructor class loader cannot find the bytecode that defined the constructor class (URL: " + url + ")");
        }

        ClassNode classNode;

        try {
            classNode = new ClassNode();
            ClassReader classReader = new ClassReader(classFileInputStream);
            classReader.accept(classNode, 0);
        } finally {
            classFileInputStream.close();
        }

        List<MethodNode> methods = classNode.methods;

        for (MethodNode method : methods) {

            if (method.name.equals("<init>") && method.desc.equals(constructorDescriptor)) {

                Type[]                  argumentTypes  = Type.getArgumentTypes(method.desc);
                List<String>            parameterNames = new ArrayList<>(argumentTypes.length);
                List<LocalVariableNode> localVariables = method.localVariables;

                for (int i = 0; i < argumentTypes.length; i++) {
                    // The first local variable represents the "this" object, hence the +1
                    parameterNames.add(localVariables.get(i + 1).name);
                }

                return parameterNames;
            }
        }

        return null;
    }
}
