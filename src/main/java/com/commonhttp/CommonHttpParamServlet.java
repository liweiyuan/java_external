package com.commonhttp;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

import static com.UrlTest.UrlParamServlet.getRandomString;

/**
 * Created by admin on 2016/11/29.
 */
public class CommonHttpParamServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>Commons http 传参</h2>");
        String param_01String, param_02String;
        param_01String = getRandomString(10);
        param_02String = getRandomString(10);
        String params = "?param_01=" + URLEncoder.encode(param_01String, "UTF-8") + "&param_02=" + URLEncoder.encode(param_02String, "UTF-8");
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/param";

        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

        PostMethod postMethod = new PostMethod(urlString + params);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        NameValuePair[] param = {new NameValuePair("postParam_01", "post参数-post参数-post参数"),
                new NameValuePair("postParam_02", getRandomString(30)),
        };
        postMethod.setRequestBody(param);
        int statusCode = client.executeMethod(postMethod);
        Header[] headers = postMethod.getResponseHeaders();
        for (int i = 0; i < headers.length; i++) {
            stringBuffer.append(headers[i].getName() + ":" + headers[i].getValue() + "<br>");
        }
        stringBuffer.append("responseCode:" + statusCode + "<br>");
        if (statusCode == 200) {
            String responseString=postMethod.getResponseBodyAsString();
            stringBuffer.append("<br>"+responseString+"<br>");
        }
        postMethod.releaseConnection();
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
