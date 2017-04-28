package com.zte.ums.esight.infra;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by root on 3/14/17.
 */
public class Invoke {
    public static Object invokePrivateMethod(Class clazz, String methodName, Class[] parameterType, Object[] parameterValue) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object instance = clazz.forName(clazz.getName()).newInstance();
        Method method = clazz.getDeclaredMethod(methodName, parameterType);
        method.setAccessible(true);
        Object result = method.invoke(instance, parameterValue);
        return result;
    }


    public static Object invokeInstancePrivateMethod(Object instance, String methodName, Class[] parameterType, Object[] parameterValue) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Method method = instance.getClass().getDeclaredMethod(methodName, parameterType);
        method.setAccessible(true);
        Object result = method.invoke(instance, parameterValue);
        return result;
    }

    public static void setFinalStatic(Field field, Object newValue) throws Exception {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        field.set(null, newValue);
    }


    public static void setPrivateField(Object instance, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, newValue);
    }

    public static void setParentField(Object instance, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getSuperclass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, newValue);
    }
}
