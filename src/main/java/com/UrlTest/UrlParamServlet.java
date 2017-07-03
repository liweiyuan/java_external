package com.UrlTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by admin on 2016/8/18.
 */
public class UrlParamServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>UrlConnection 传参</h2>");
        String param_01String, param_02String;
        param_01String = getRandomString(10);
        param_02String = getRandomString(10);
        String params = "?param_01=" + URLEncoder.encode(param_01String, "UTF-8") + "&param_02=" + URLEncoder.encode(param_02String, "UTF-8");
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/param";
        HttpURLConnection connection = null;
        try {
            connection = UrlUtil.createConnection(urlString + params);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            OutputStream outputStream = connection.getOutputStream();
            String postParamString = "post参数-post参数-post参数";
            String postParamString2 = getRandomString(30);
            String data = "postParam_01=" + postParamString + ";" + "postParam_02=" + postParamString2 + ";";
            outputStream.write(data.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();
            //   int statusCode = connection.getResponseCode();
            Map map=UrlUtil.getResponsecodeAndTime(connection);
            int statusCode=(Integer) map.get("statusCode");
            long during=(Long) map.get("duringTime");
            stringBuffer.append("during time is "+during+" .responseCode:" + statusCode + "<br>");
            stringBuffer.append(UrlUtil.getConnectionHeader(connection));
            if (statusCode == HttpURLConnection.HTTP_OK) {
                stringBuffer.append(UrlUtil.getConnectionInputString(connection));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "你我他的地のabcxyz0123789~!@#$%^&*();/<>|\\][{}（），。、《》？；‘：“”’";
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            stringBuffer.append(base.charAt(number));
        }
        return stringBuffer.toString();
    }

}
