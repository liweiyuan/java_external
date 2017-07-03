package com.httpclient;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static com.UrlTest.UrlParamServlet.getRandomString;

/**
 * Created by admin on 2016/11/29.
 */
public class HttpClientParamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>httpClient 传参</h2>");
        String param_01String, param_02String;
        param_01String = getRandomString(10);
        param_02String = getRandomString(10);
        String params = "?param_01=" + URLEncoder.encode(param_01String, "UTF-8") + "&param_02=" + URLEncoder.encode(param_02String, "UTF-8");
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/param";


        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(urlString+params);
        httppost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        List bodyparams = new ArrayList();
        bodyparams.add(new BasicNameValuePair("postParam_01", "post参数-post参数-post参数"));
        bodyparams.add(new BasicNameValuePair("postParam_02", getRandomString(30)));

        httppost.setEntity(new UrlEncodedFormEntity(bodyparams,"UTF-8"));
        HttpResponse response = httpclient.execute(httppost);
        Header[] headers = response.getAllHeaders();
        int statusCode=response.getStatusLine().getStatusCode();
        for (int i=0; i<headers.length; i++) {
            stringBuffer.append(headers[i]+"<br>");
        }
        stringBuffer.append("responseCode:" + statusCode + "<br>");
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseString= EntityUtils.toString(entity);
            stringBuffer.append("<br>"+responseString+"<br>");
        }
        httppost.abort();
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
