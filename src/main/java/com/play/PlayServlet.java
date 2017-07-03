package com.play;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tingyun on 2017/5/24.
 */
public class PlayServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod("http://localhost:9000/get");
        //httpClient.getHttpConnectionManager().
        String proxyHost = "192.168.3.2";
        int proxyPort = 8080;
        //httpClient.getCredentialsProvider().setCredentials(new AuthScope(proxyHost, proxyPort),new UsernamePasswordCredentials("wade","java"));
        httpClient.getHostConfiguration().setProxy(proxyHost,proxyPort);
        httpClient.executeMethod(getMethod);
        resp.getWriter().println("successs");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
