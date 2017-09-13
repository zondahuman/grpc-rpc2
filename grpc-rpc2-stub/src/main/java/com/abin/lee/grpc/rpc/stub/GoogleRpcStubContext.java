package com.abin.lee.grpc.rpc.stub;


import com.abin.lee.grpc.rpc.stub.common.GoogleRpcRemoteAddress;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;

/**
 * 服务端注册服务工厂
 */
@Component
public class GoogleRpcStubContext implements FactoryBean, InitializingBean, Closeable {
    private GoogleRpcRemoteAddress remoteAddress;
    private String service;

    private Object proxyClient;
    private Class<?> objectClass;

    @Override
    public void close() throws IOException {

    }

    @Override
    public Object getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 加载Iface接口
        objectClass = classLoader.loadClass(service);
        String grpcName = objectClass.getSimpleName();
        String allName = objectClass.getName();
        grpcName = grpcName.substring(grpcName.lastIndexOf("."), grpcName.indexOf("Grpc"));
        Class<?> targetClass = classLoader.loadClass(allName + "$"+grpcName+"BlockingStub");

        System.out.println("targetClass="+targetClass);
//        BindableService


    }

    public GoogleRpcRemoteAddress getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(GoogleRpcRemoteAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
