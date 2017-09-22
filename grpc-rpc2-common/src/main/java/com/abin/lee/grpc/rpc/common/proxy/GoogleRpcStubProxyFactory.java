package com.abin.lee.grpc.rpc.common.proxy;


import com.abin.lee.grpc.rpc.common.rpc.GoogleRpcRemoteAddress;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;

import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 */
public class GoogleRpcStubProxyFactory implements FactoryBean, InitializingBean, Closeable, Ordered {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private Object service;
    private GoogleRpcRemoteAddress googleRpcRemoteAddress;
    private Object proxyClient;
    private Class<?> objectClass;
    ManagedChannel channel = null;

    @Override
    public void close() throws IOException {
        if(!channel.isShutdown()){
            channel.shutdown();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        channel = NettyChannelBuilder.forAddress(googleRpcRemoteAddress.getHost(), googleRpcRemoteAddress.getPort()).usePlaintext(true).build();
        proxyClient = createClientProxy(channel);
        LOGGER.info("proxyClient = " + proxyClient);
    }

    public  <T> T createClientProxy(io.grpc.Channel channel) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object clientProxy = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<?> externalClass = classLoader.loadClass((String)service);
        LOGGER.info("externalClass=" + externalClass);
        clientProxy = GoogleGrpcStubUtil.newClient(externalClass, channel);
        LOGGER.info("clientProxy = " + clientProxy);
        return (T)clientProxy;
    }



    public GoogleRpcRemoteAddress getGoogleRpcRemoteAddress() {
        return googleRpcRemoteAddress;
    }

    public void setGoogleRpcRemoteAddress(GoogleRpcRemoteAddress googleRpcRemoteAddress) {
        this.googleRpcRemoteAddress = googleRpcRemoteAddress;
    }

    @Override
    public Object getObject() throws Exception {
        return proxyClient;
    }

    @Override
    public Class<?> getObjectType() {
        return objectClass;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public int getOrder() {
        return 1;
    }

    public Object getProxyClient() {
        return proxyClient;
    }

    public void setProxyClient(Object proxyClient) {
        this.proxyClient = proxyClient;
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    public Object getService() {
        return service;
    }

    public void setService(Object service) {
        this.service = service;
    }



}
