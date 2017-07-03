package com.httpclient.HttpclientExternalError;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by admin on 2016/11/29.
 */
public class Error904Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>httpclient External Error</h2>");
        String urlString = "svn://localhost/javaagent";
        HttpClient httpclient = new DefaultHttpClient();
        try {
            HttpGet httpget = new HttpGet(urlString);
            HttpResponse response = httpclient.execute(httpget);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            stringBuffer.append(e.getLocalizedMessage() + "<br>");
        }

        stringBuffer.append("---904 ClientProtocolException---");
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }
}

