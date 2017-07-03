package com.UrlTest.UrlExternalError;

import com.UrlTest.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by admin on 2016/8/19.
 */
public class MultipleUrlErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>UrlConnection External Error</h2>");
        String urlString404 = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/xx";
        String urlString500 = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/500";
        String urlString900 = "svn://localhost/javaagent";
        String urlString901 = "http://localhosthost:8080/test";
        String urlString902 = "http://192.158.2.158:8080/test";
        String urlString903 = req.getScheme() + "://" + req.getServerName() + ":" + "8070" + req.getContextPath() + "/httpserver";
        String urlString908 = "https://192.168.2.106/info.php";

        String urlStr[] = {
                urlString404, urlString500, urlString901, urlString902, urlString908, urlString404, urlString901,
        };
        stringBuffer.append("发生外部服务错误数为" + urlStr.length + "<br>");

        HttpURLConnection connection = null;
        for (int i = 0; i < urlStr.length; i++) {
            try {
                stringBuffer.append(urlStr[i] + "<br>");
                URL url = new URL(urlStr[i]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (connection != null) {
                    // connection=null;
                    connection.disconnect();
                }
            }
        }
        stringBuffer.append("--- 一个webaction发生多次外部服务错误 ---");
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
