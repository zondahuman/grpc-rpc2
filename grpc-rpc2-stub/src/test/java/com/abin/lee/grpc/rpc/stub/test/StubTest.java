package com.abin.lee.grpc.rpc.stub.test;

import io.grpc.Channel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;

/**
 * Created by abin on 2017/9/12 19:22.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.skeleton.test
 */
public class StubTest {

    public static void main(String[] args) throws ClassNotFoundException {
        String className = "java.util.Date";
        Class c1 = Class.forName(className);
        System.out.println(c1.getSimpleName());
    }


    public <T> T newClient(Class<T> clientType, io.grpc.Channel channel) {
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


}
