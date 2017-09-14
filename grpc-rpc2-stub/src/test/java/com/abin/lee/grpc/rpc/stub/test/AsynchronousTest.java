package com.abin.lee.grpc.rpc.stub.test;

import com.abin.lee.grpc.rpc.common.util.DateUtil;
import javafx.util.converter.BigDecimalStringConverter;
import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by abin on 2017/9/12 19:22.
 * grpc-rpc2
 * com.abin.lee.grpc.rpc.skeleton.test
 */
public class AsynchronousTest {

    public static void main(String[] args) throws ClassNotFoundException, InterruptedException {
        test2();
    }

    public static void test2(){
        String result = "0.001";
        BigDecimal bigDecimal = new BigDecimal(result);
        System.out.println("bigDecimal= "+ bigDecimal);
//        int r=big_decimal.compareTo(BigDecimal.Zero); //和0，Zero比较
//        if(r==0) //等于
//            if(r==1) //大于
//                if(r==-1) //小于
//                    或者
//        if(big_decimal.equals(BigDecimal.Zero)) //是否等于0
        int compare = bigDecimal.compareTo(BigDecimal.ZERO);
        System.out.println("compare= "+ compare);

    }

    public static void test1()throws ClassNotFoundException, InterruptedException{
        String className = "java.util.Date";
        Class c1 = Class.forName(className);
        System.out.println(c1.getSimpleName());
        System.out.println("Synchronous-Date1= "+ DateUtil.getYMDHMSTime());
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Asynchronous-Date= "+ DateUtil.getYMDHMSTime());
            }
        }).start();
//        Thread.sleep(5000L);
        System.out.println("Synchronous-Date2= "+ DateUtil.getYMDHMSTime());

        Integer delay = 1;
        Date startDate = DateUtils.addMinutes(new Date(), delay * (-1));
        System.out.println("Synchronous-startDate= "+ DateUtil.getYMDHMSTime(startDate));
    }



}
