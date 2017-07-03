package com.commonhttp;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by admin on 2016/11/29.
 */
public class CommonHttpGetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>common http 调用</h2>");
        if (req.getParameter("commonshttpGetUrl") == null) {
            urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/test";
        } else {
            urlString = (String) req.getParameter("commonshttpGetUrl");
            String queryString = req.getQueryString();
            urlString = URLDecoder.decode(req.getQueryString().toString().substring(queryString.indexOf("=") + 1), "UTF-8");
            if (!urlString.contains("http")) {
                String urlpre = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
                urlString = urlpre + "/" + urlString;
            }
        }
        stringBuffer.append(urlString + "<br>");

        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

        GetMethod getMethod = new GetMethod(urlString);
        int statusCode = client.executeMethod(getMethod);

        Header[] headers = getMethod.getResponseHeaders();
        for (int i = 0; i < headers.length; i++) {
            stringBuffer.append(headers[i].getName() + ":" + headers[i].getValue() + "<br>");
        }
        stringBuffer.append("responseCode:" + statusCode + "<br>");
        if (statusCode == 200) {
            String responseString=getMethod.getResponseBodyAsString();
            stringBuffer.append("<br>"+responseString+"<br>");
        }
        getMethod.releaseConnection();
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }
}
