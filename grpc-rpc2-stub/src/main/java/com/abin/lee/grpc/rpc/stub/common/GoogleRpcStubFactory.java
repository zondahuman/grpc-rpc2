package com.abin.lee.grpc.rpc.stub.common;


import io.grpc.ManagedChannel;
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
