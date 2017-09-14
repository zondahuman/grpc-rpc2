package com.abin.lee.grpc.rpc.stub;


import com.abin.lee.grpc.rpc.stub.common.GoogleRpcStubFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;

/**
 * 服务端注册服务工厂
 */
@Component
public class GoogleRpcStubContext implements  InitializingBean, Closeable, Ordered {
    private String service;
    private GoogleRpcStubFactory stubFactory;
    private Object proxyClient;
    private Class<?> objectClass;


    @Override
    public void close() throws IOException {

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        service = stubFactory.getService().toString();
        // 加载Iface接口
        objectClass = classLoader.loadClass(service);
        String grpcName = objectClass.getSimpleName();
        String allName = objectClass.getName();
        grpcName = grpcName.substring(grpcName.lastIndexOf("."), grpcName.indexOf("Grpc"));
        Class<?> targetClass = classLoader.loadClass(allName + "$"+grpcName+"BlockingStub");

        System.out.println("targetClass="+targetClass);
//        BindableService


    }



    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public GoogleRpcStubFactory getStubFactory() {
        return stubFactory;
    }

    public void setStubFactory(GoogleRpcStubFactory stubFactory) {
        this.stubFactory = stubFactory;
    }

    @Override
    public int getOrder() {
        return 1;
    }


}
