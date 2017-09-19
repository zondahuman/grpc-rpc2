package com.abin.lee.grpc.rpc.common.proxy;

import com.google.common.collect.Lists;
import io.grpc.Channel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * Created by abin on 2017/9/18 11:17.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.common
 */
public class GoogleGrpcStubUtil {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        String className = "com.abin.lee.grpc.rpc.service.OrderServiceGrpc";
        Class clazz = Class.forName(className);
        System.out.println(clazz.getSimpleName());
        List<Object> list = getStaticInnerClass(clazz);
        System.out.println("list" + list);
    }


    public static <T> T  createClientProxy(String service, Channel channel) throws ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> externalClass = classLoader.loadClass((String)service);
        String serviceType = "block";
        T  targetClass = (T)newClient(externalClass, channel);
        return targetClass;
    }

    public static <T> T newClient(Class<T> clientType, io.grpc.Channel channel) {
        final Class<?> stubClass = clientType.getEnclosingClass();
        if (stubClass == null) {
            throw new IllegalArgumentException("Client type not a gRPC client stub class, " +
                    "should be something like ServiceNameGrpc.ServiceNameXXStub: " +
                    clientType);
        }
        final Method newStubMethod;
        final Method newBlockingStubMethod;
        final Method newFutureStubMethod;
        try {
            newStubMethod = stubClass.getDeclaredMethod("newStub", Channel.class);
            newBlockingStubMethod = stubClass.getDeclaredMethod("newBlockingStub", Channel.class);
            newFutureStubMethod = stubClass.getDeclaredMethod("newFutureStub", Channel.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Client type not a gRPC client stub class, " +
                    "should be something like ServiceNameGrpc.ServiceNameXXStub: " +
                    clientType, e);
        }
        final Method createClientMethod;
        if (newStubMethod.getReturnType() == clientType) {
            createClientMethod = newStubMethod;
        } else if (newBlockingStubMethod.getReturnType() == clientType) {
            createClientMethod = newBlockingStubMethod;
        } else if (newFutureStubMethod.getReturnType() == clientType) {
            createClientMethod = newFutureStubMethod;
        } else {
            throw new IllegalArgumentException("Client type not a gRPC client stub class, " +
                    "should be something like ServiceNameGrpc.ServiceNameXXStub: " +
                    clientType);
        }
        try {
            // Verified createClientMethod.getReturnType == clientType
            @SuppressWarnings("unchecked")
            T stub = (T) createClientMethod.invoke(null, channel);
            return stub;
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new IllegalStateException("Could not create stub through reflection.", e);
        }
    }

    public static List<Object> getStaticInnerClass(Class<?> clazz) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        Class innerClazz[] = clazz.getDeclaredClasses();
        List<Object> list = Lists.newArrayList();
        for (Class cls : innerClazz) {
            int mod = cls.getModifiers();
            String modifier = Modifier.toString(mod);
            if (modifier.contains("static")) {
                //构造静态内部类实例
//              Constructor con1 = cls.getDeclaredConstructor();
                Object object = cls.getName();
                list.add(object);
            }
        }
        return list;
    }



}
