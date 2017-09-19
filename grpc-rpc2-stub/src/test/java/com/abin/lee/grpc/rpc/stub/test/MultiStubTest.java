package com.abin.lee.grpc.rpc.stub.test;

import com.abin.lee.grpc.rpc.service.OrderServiceGrpc;
import com.abin.lee.grpc.rpc.stub.proxy.GoogleGrpcStubUtil;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;

/**
 * Created by abin on 2017/9/18 11:41.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.stub.test
 */
public class MultiStubTest {

    public static void main(String[] args) throws Exception {
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 10086).usePlaintext(true).build();
        OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub = GoogleGrpcStubUtil.createClientProxy("com.abin.lee.grpc.rpc.service.OrderServiceGrpc$OrderServiceBlockingStub", channel);
//        OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub = GoogleGrpcStubUtil.createClientProxy("com.abin.lee.grpc.rpc.service.OrderServiceGrpc", channel);
        System.out.println("orderServiceBlockingStub: "+orderServiceBlockingStub);
        OrderServiceGrpc.OrderServiceStub orderServiceStub = GoogleGrpcStubUtil.createClientProxy("", channel);
        System.out.println("orderServiceStub: "+orderServiceStub);
        OrderServiceGrpc.OrderServiceFutureStub orderServiceFutureStub = GoogleGrpcStubUtil.createClientProxy("", channel);
        System.out.println("orderServiceFutureStub: "+orderServiceFutureStub);


//        String className = "com.abin.lee.grpc.rpc.service.OrderServiceGrpc$OrderServiceBlockingStub";
//        String className = "com.abin.lee.grpc.rpc.service.OrderServiceGrpc";
//        Class clazz = Class.forName(className);
//        System.out.println(clazz.getSimpleName());
//        List<Object> list = GoogleGrpcStubUtil.getStaticInnerClass(clazz);
//        System.out.println("list" + list);
    }


}
