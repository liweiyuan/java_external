package com.httpclient.HttpclientExternalError;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

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
        stringBuffer.append("<html><head><title></title></head><body><h2>httpclient External Error</h2>");

        String httpserverPort = "8070";
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + httpserverPort + "/httpserver";
        HttpParams httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, 300);
        HttpConnectionParams.setSoTimeout(httpParams, 300);
        HttpClient httpclient = new DefaultHttpClient(httpParams);
        try {
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
            } else {

            }
            httpget.abort();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            stringBuffer.append(e.getLocalizedMessage() + "<br>");
            stringBuffer.append("---903-SocketTimeoutException---");

        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append(e.getLocalizedMessage() + "<br>");
            stringBuffer.append("---903-ConnectTimeoutException---");

        }
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }
}
