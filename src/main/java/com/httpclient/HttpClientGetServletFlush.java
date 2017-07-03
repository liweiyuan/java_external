package com.httpclient;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by tingyun on 2017/6/6.
 */
public class HttpClientGetServletFlush extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>HttpClient 调用</h2>");
        if (req.getParameter("httpclientGetUrl") == null) {
            urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/flush";
            //urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/hello.jsp";
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
        HttpGet httpget = new HttpGet(urlString);
        HttpResponse response = httpclient.execute(httpget);
        int statusCode = response.getStatusLine().getStatusCode();
        Header[] headers = response.getAllHeaders();
        for (int i = 0; i < headers.length; i++) {
            stringBuffer.append(headers[i] + "<br>");
        }
        stringBuffer.append("responseCode:" + statusCode + "<br>");
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity);
            stringBuffer.append("<br>" + responseString + "<br>");
        }
        httpget.abort();
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());
        //req.getRequestDispatcher("/hello.jsp").forward(req,resp);
    }
}
