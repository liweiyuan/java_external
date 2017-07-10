package com.httpclient;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class HttoClientPostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost post = new HttpPost("http://localhost:8885/browsertest/contentlengthServlet1");
        post.getParams().setParameter(HttpMethodParams.USER_AGENT, "test idea");
        //post.getParams().setParameter(CoreConnectionPNames.USER_AGENT, "test idea");

        CloseableHttpResponse response = client.execute(post);
        String content = EntityUtils.toString(response.getEntity());
        System.out.println(content);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}
