package com.httpclient;


import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by admin on 2016/11/29.
 */
public class HttpClientPostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>httpClient 调用</h2>");
        if (req.getParameter("httpclientGetUrl") == null) {
            urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/test";
        } else {
            urlString = (String) req.getParameter("httpclientGetUrl");
            String queryString = req.getQueryString();
            urlString = URLDecoder.decode(req.getQueryString().toString().substring(queryString.indexOf("=") + 1), "UTF-8");
            if (!urlString.contains("http")) {
                String urlpre = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
                urlString = urlpre + "/" + urlString;
            }
        }
        stringBuffer.append(urlString + "<br>");

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(urlString);
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
}
