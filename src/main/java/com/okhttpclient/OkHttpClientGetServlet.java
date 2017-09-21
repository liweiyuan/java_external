package com.okhttpclient;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tingyun on 2017/8/29.
 * 验证okHttp
 */
public class OkHttpClientGetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>okHttpClient get 调用</h2>");

        urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/test?a=java";
        if (urlString.contains("http")) {
            String urlpre = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
            //urlString = urlpre + "/" + urlString;
            stringBuffer.append(urlString + "<br>");

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(urlString).build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                Headers headers = response.headers();
                stringBuffer.append(headers);
            }
            stringBuffer.append(" </body></html>");
            resp.getWriter().write(stringBuffer.toString());

        }
    }
}
