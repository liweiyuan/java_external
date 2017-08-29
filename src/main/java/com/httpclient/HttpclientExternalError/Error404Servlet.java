package com.httpclient.HttpclientExternalError;


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
 * Created by admin on 2016/11/29.
 */
public class Error404Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/404";

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(urlString);
        HttpResponse response = httpclient.execute(httpget);
        stringBuffer.append("<html><head><title></title></head><body><h2>httpclient External Error</h2>");
        stringBuffer.append("---404 HTTP Error---");
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }
}
