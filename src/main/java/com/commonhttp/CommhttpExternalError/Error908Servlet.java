package com.commonhttp.CommhttpExternalError;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.net.ssl.SSLHandshakeException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2016/11/29.
 */
public class Error908Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>commonshttp External Error</h2>");
        String urlString = "https://192.168.2.106/info.php";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        try {
            GetMethod getMethod = new GetMethod(urlString);
            client.executeMethod(getMethod);
        } catch (SSLHandshakeException e) {
            e.printStackTrace();
            stringBuffer.append(e.getLocalizedMessage() + "<br>");
        }
        stringBuffer.append("---908-SSLHandshakeException---");
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }
}
