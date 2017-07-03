package com.UrlTest;

import org.apache.commons.httpclient.HttpURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by admin on 2017/1/6.
 */
public class UrlTest extends HttpServlet {
    public static HttpURLConnection getHttpConnection(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(50000);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("content-type", "application/octet-stream;charset=UTF-8");
        return conn;
    }

    public String urlTest(HttpServletRequest request) throws IOException {
        String connURL = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/test";
        HttpURLConnection httpConn = getHttpConnection(connURL);
        InputStream in = null;
        try {
            if (null == httpConn) {
                return null;
            }
            httpConn.connect();

            InputStream is = httpConn.getInputStream();
            String str = null;
            StringBuilder buffer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            while ((str = br.readLine()) != null) {
                buffer.append(str);
            }
            br.close();
            String data = buffer.toString();
            return (data == null || data.length() == 0) ? null : data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (httpConn != null) {
                    httpConn.disconnect();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(urlTest(req));

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}