package com.abin.lee.grpc.rpc.stub.controller;

import com.abin.lee.grpc.rpc.service.*;
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
    @Resource
    TeamServiceGrpc.TeamServiceBlockingStub teamService;
    @Resource
    VoucherServiceGrpc.VoucherServiceBlockingStub voucherService;

    @RequestMapping(value = "/createOrder")
    @ResponseBody
    public String createOrder() throws TException {
        OrderRequest orderRequest = OrderRequest.newBuilder().setRecipient("admin@google.com").setTitle("Email Title").setContent("This is email content").build();

        OrderResponse orderResponse = this.orderService.createOrder(orderRequest);
        System.out.println(orderResponse.toString());
        return orderResponse.toString();
    }


    @RequestMapping(value = "/findTeamListById")
    @ResponseBody
    public String findTeamListById() throws TException {
        TeamRequest teamRequest = TeamRequest.newBuilder().setTeamName("FISH").setBusinessId(1000000000L).setTeamAddress("peking").setTeamPrice(298.00).build();
        RootResponse teamResponse = this.teamService.findListById(teamRequest);
        System.out.println(teamResponse.toString());
        return teamResponse.toString();
    }



    @RequestMapping(value = "/findAllVoucherList")
    @ResponseBody
    public String findAllVoucherList() throws TException {
        emptyRequest emptyParam = emptyRequest.newBuilder().build();
        VoucherResponse voucherResponse = this.voucherService.findAll(emptyParam);
        System.out.println(voucherResponse.toString());
        return voucherResponse.toString();
    }



}
