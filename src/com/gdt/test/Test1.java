package com.gdt.test;



import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import java.net.URLDecoder;

public class Test1 {
    public static void main(String[] args) {


        int responseCode = 0;
        try {
            String url = "http://localhost:8080/test1/fileupload.html";
            HttpClient client = new HttpClient();
            PostMethod postMethod = new PostMethod(url);
           // postMethod.setParameter("OAMessage", "410000000021");

            responseCode = client.executeMethod(postMethod);
            if (responseCode == 200) {
               // String callBackString = postMethod.getResponseBodyAsString();
                System.out.println("请求返回结果为:" +"success");

            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("网页代码" + responseCode);

    }
}
