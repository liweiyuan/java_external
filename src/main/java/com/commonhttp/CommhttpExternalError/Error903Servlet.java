package com.commonhttp.CommhttpExternalError;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by admin on 2016/11/29.
 */
public class Error903Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>commonshttp External Error</h2>");

        String httpserverPort = "8070";
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + httpserverPort + "/httpserver";
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(300);
        client.getHttpConnectionManager().getParams().setSoTimeout(300);

        try {

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
                }else {

            }
            getMethod.releaseConnection();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            stringBuffer.append(e.getLocalizedMessage()+"<br>");
            stringBuffer.append("---903-SocketTimeoutException---");

        }catch(ConnectTimeoutException e){
               e.printStackTrace();            stringBuffer.append(e.getLocalizedMessage()+"<br>");
            stringBuffer.append("---903-ConnectTimeoutException---");

        }
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }
}
