package com.abin.lee.grpc.rpc.skeleton;


import com.abin.lee.grpc.rpc.common.context.SpringContextUtils;
import com.abin.lee.grpc.rpc.skeleton.common.GoogleRpcSkeletonFactory;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.NettyServerBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;

/**
 * 服务端注册服务工厂
 */
@Component
public class GoogleRpcSkeletonContext {
    // 端口
    private Integer port;
    //主机
    private String host;

    @PostConstruct
    public void init() throws Exception {
        NettyServerBuilder builder = NettyServerBuilder.forPort(10086);

        Map<String, GoogleRpcSkeletonFactory> handlers = SpringContextUtils.getBeansOfType(GoogleRpcSkeletonFactory.class);
        for (Iterator<Map.Entry<String, GoogleRpcSkeletonFactory>> iterator = handlers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, GoogleRpcSkeletonFactory> entry = iterator.next();
            String beanName = entry.getKey();
            GoogleRpcSkeletonFactory instance = entry.getValue();
            Object service = instance.getService();
            Class<?> serviceClass = service.getClass();
            builder.addService((BindableService)service);
        }

        Server server = builder.build();
        server.start();

        System.out.println("............................................................................................................................");
        System.out.println(".................................................GRPC Server is Running now!................................................");
        System.out.println("............................................................................................................................");

//        System.in.read(); // 按任意键退出
        server.awaitTermination();
    }



}
