package com.abin.lee.grpc.rpc.stub.test;

import com.abin.lee.grpc.rpc.common.util.HttpClientUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: abin Date: 16-4-28 Time: 下午7:02 To change
 * this template use File | Settings | File Templates.
 */
public class FindAllVoucherListTest {
    private static String httpURL = "http://localhost:7100/stub/findAllVoucherList";
//    private static String httpURL = "http://localhost:8300/stub/findAllVoucherList";

    @Test
    public void testFindAllVoucherList() {
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("name", "lee"));
            nvps.add(new BasicNameValuePair("age", "30"));
            HttpPost httpPost = new HttpPost(httpURL);

            httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
            System.out.println("Executing request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }







}
