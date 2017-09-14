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

    ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 10086).usePlaintext(true).build();
    @Resource
    GoogleRpcRemoteAddress googleRpcRemoteAddress;

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
            service = instance.getService();
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        service = stubFactory.getService().toString();
            // 加载Iface接口
//            objectClass = classLoader.loadClass((String)service);
            String grpcName = objectClass.getSimpleName();
            String allName = objectClass.getName();
            String prefixName = grpcName.substring(0, grpcName.indexOf("Grpc"));
            objectClass = classLoader.loadClass(allName + "$"+prefixName+"BlockingStub");

            System.out.println("objectClass="+objectClass);

            Constructor<?> constructor = objectClass.getConstructor(clazz);
            processor = (TProcessor) constructor.newInstance(service);

            proxyClient = Proxy.newProxyInstance(classLoader, new Class[] { objectClass }, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    TServiceClient client = pool.borrowObject();
                    boolean flag = true;
                    try {
                        System.out.println("objectClass="+objectClass);
                        System.out.println("client.getClass().getName()="+ client.getClass().getName());
                        return method.invoke(client, args);
                    } catch (Exception e) {
                        flag = false;
                        throw e;
                    } finally {

                    }
                }
            });

        }


//        BindableService


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


}
