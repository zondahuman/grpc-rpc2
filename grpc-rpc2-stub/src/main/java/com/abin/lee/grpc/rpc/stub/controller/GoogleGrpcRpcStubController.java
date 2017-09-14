package com.abin.lee.grpc.rpc.stub.controller;

import com.abin.lee.grpc.rpc.service.OrderRequest;
import com.abin.lee.grpc.rpc.service.OrderResponse;
import com.abin.lee.grpc.rpc.service.OrderServiceGrpc;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by abin on 2017/9/15 2017/9/15.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.stub.controller
 */
@Controller
@RequestMapping("/stub")
public class GoogleGrpcRpcStubController {

    @Resource
    OrderServiceGrpc.OrderServiceBlockingStub orderService;


    @RequestMapping(value = "/createOrder")
    @ResponseBody
    public String createOrder() throws TException {
        OrderRequest orderRequest = OrderRequest.newBuilder().setRecipient("admin@google.com").setTitle("Email Title").setContent("This is email content").build();

        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        System.out.println(orderResponse.toString());
        return orderResponse.toString();
    }



}
