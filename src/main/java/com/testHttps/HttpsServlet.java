package com.testHttps;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tingyun on 2017/7/12.
 */
public class HttpsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpsClientTest httpsClient=new HttpsClientTest();
        //httpsµ÷ÓÃ
        httpsClient.testIt();
        //resp.getWriter().println("success");
        HttpsClientTest httpsClient2=new HttpsClientTest();
        httpsClient2.testIt2();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
