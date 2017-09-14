package com.abin.lee.grpc.rpc.stub;


import com.abin.lee.grpc.rpc.common.context.SpringContextUtils;
import com.abin.lee.grpc.rpc.stub.common.GoogleRpcRemoteAddress;
import com.abin.lee.grpc.rpc.stub.common.GoogleRpcStubFactory;
import io.grpc.BindableService;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import org.apache.thrift.TProcessor;
import org.apache.thrift.TServiceClient;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Iterator;
import java.util.Map;

/**
 * 服务端注册服务工厂
 */
@Component
public class GoogleRpcStubContext implements FactoryBean, InitializingBean, Closeable, Ordered {
    private Object proxyClient;
    private Class<?> objectClass;
    private Object service;
    ManagedChannel channel = null;
    @Resource
    GoogleRpcRemoteAddress googleRpcRemoteAddress;

    @Override
    public void close() throws IOException {

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        channel = NettyChannelBuilder.forAddress(googleRpcRemoteAddress.getHost(), googleRpcRemoteAddress.getPort()).usePlaintext(true).build();
        Object service = null;
        Map<String, GoogleRpcStubContext> handlers = SpringContextUtils.getBeansOfType(GoogleRpcStubContext.class);
        for (Iterator<Map.Entry<String, GoogleRpcStubContext>> iterator = handlers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, GoogleRpcStubContext> entry = iterator.next();
            String beanName = entry.getKey();
            GoogleRpcStubContext instance = entry.getValue();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        service = stubFactory.getService().toString();
            // 加载Iface接口
            Class<?> externalClass = classLoader.loadClass((String) service);
//            String grpcName = externalClass.getSimpleName();
//            String allName = externalClass.getName();
//            String prefixName = grpcName.substring(0, grpcName.indexOf("Grpc"));
//            objectClass = classLoader.loadClass(allName + "$" + prefixName + "BlockingStub");

            System.out.println("externalClass=" + externalClass);

            Method newBlockingStub = externalClass.getMethod("newBlockingStub", io.grpc.Channel.class);

            Object[] object = {channel};
            proxyClient = newBlockingStub.invoke(externalClass, object);
//            proxyClient = newBlockingStub.invoke(externalClass, channel);

            System.out.println("proxyClient = " + proxyClient);
//            newBlockingStub.invoke()
//            Constructor<?> constructor = objectClass.getConstructor(ManagedChannel.class);
//            processor = (TProcessor) constructor.newInstance(service);
//            Constructor<?> cons = cls.getConstructor(String.class, int.class);
//            Object obj = cons.newInstance("张三", 20); // 为构造方法传递参数
//
//
//            proxyClient = Proxy.newProxyInstance(classLoader, new Class[] { objectClass }, new InvocationHandler() {
//                @Override
//                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//
//                    TServiceClient client = pool.borrowObject();
//                    boolean flag = true;
//                    try {
//                        System.out.println("objectClass="+objectClass);
//                        System.out.println("client.getClass().getName()="+ client.getClass().getName());
//                        return method.invoke(client, args);
//                    } catch (Exception e) {
//                        flag = false;
//                        throw e;
//                    } finally {
//
//                    }
//                }
//            });
//
//        }


//        BindableService

        }
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
