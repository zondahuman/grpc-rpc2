package com.abin.lee.grpc.rpc.common.proxy.test;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by abin on 2017/9/15 15:11.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.common.proxy.test
 */
public class AbstractClassReflectTest {

    public static void main(String[] args) {

    }

    @Test
    public void test1() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> externalClass = classLoader.loadClass("io.grpc.stub.AbstractStub");

        Constructor<?> constructor = externalClass.getConstructor(io.grpc.Channel.class);
        System.out.println("constructor = " + constructor);

        Object proxyClient = constructor.newInstance(externalClass);
        System.out.println("proxyClient = " + proxyClient);
    }

    @Test
    public void test2() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> externalClass = classLoader.loadClass("io.grpc.stub.AbstractStub");

        Constructor<?> constructor = externalClass.getConstructor(io.grpc.Channel.class);
        System.out.println("constructor = " + constructor);

        Object proxyClient = constructor.newInstance(externalClass);
        System.out.println("proxyClient = " + proxyClient);
    }


}
