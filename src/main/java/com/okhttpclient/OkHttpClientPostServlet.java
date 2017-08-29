package com.okhttpclient;

import com.squareup.okhttp.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by tingyun on 2017/8/29.
 */
public class OkHttpClientPostServlet extends HttpServlet {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>okHttpClient post 调用</h2>");

        urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/test";
        if (urlString.contains("http")) {
            stringBuffer.append(urlString + "<br>");
            OkHttpClient okHttpClient =new OkHttpClient();
            /*Request request=new Request.Builder().url(urlString).build();
            Response response=okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                Headers headers=response.headers();
                stringBuffer.append(headers);
            }*/

            RequestBody formBody = new FormEncodingBuilder()
                    .add("platform", "android")
                    .add("name", "bug")
                    .add("subject", "XXXXXXXXXXXXXXX")
                    .build();
            //发送post请求
            Request request=new Request.Builder().url(urlString).post(formBody).build();

            //获取响应
            Response response=okHttpClient.newCall(request).execute();
            if(response.isSuccessful()){
                Headers headers=response.headers();
                stringBuffer.append(headers);
            }
            stringBuffer.append(" </body></html>");
            resp.getWriter().write(stringBuffer.toString());
        }
    }


}
