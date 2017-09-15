package com.abin.lee.grpc.rpc.stub.common;


import com.abin.lee.grpc.rpc.common.context.SpringContextUtils;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

/**
 * 服务端注册服务工厂
 */

public class GoogleRpcStubFactory implements FactoryBean, InitializingBean, Closeable, Ordered {

    // 服务实现类
    private Object service;// serice实现类

    private GoogleRpcRemoteAddress googleRpcRemoteAddress;
    private Object proxyClient;
    private Class<?> objectClass;
    ManagedChannel channel = null;

    @Override
    public void close() throws IOException {

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        channel = NettyChannelBuilder.forAddress(googleRpcRemoteAddress.getHost(), googleRpcRemoteAddress.getPort()).usePlaintext(true).build();
        Object service = null;
        Map<String, GoogleRpcStubFactory> handlers = SpringContextUtils.getBeansOfType(GoogleRpcStubFactory.class);
        for (Iterator<Map.Entry<String, GoogleRpcStubFactory>> iterator = handlers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, GoogleRpcStubFactory> entry = iterator.next();
            String beanName = entry.getKey();
            GoogleRpcStubFactory instance = entry.getValue();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> externalClass = classLoader.loadClass((String) instance.getService());
            System.out.println("externalClass=" + externalClass);
            Method newBlockingStub = externalClass.getMethod("newBlockingStub", io.grpc.Channel.class);
            Object[] object = {channel};
            proxyClient = newBlockingStub.invoke(externalClass, object);
//            proxyClient = newBlockingStub.invoke(externalClass, channel);

            System.out.println("proxyClient = " + proxyClient);
        }
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
