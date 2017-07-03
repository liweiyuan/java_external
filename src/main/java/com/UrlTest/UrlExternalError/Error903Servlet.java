package com.UrlTest.UrlExternalError;

import com.UrlTest.UrlUtil;

import javax.print.DocFlavor;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by admin on 2016/8/19.
 */
public class Error903Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>UrlConnection External Error</h2>");
        String httpserverPort = "8070";
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + httpserverPort + "/httpserver";
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Charsert", "UTF-8");
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(3000);
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestProperty("content-type", "application/octet-stream;charset=UTF-8");
        //URLConnection connection=UrlUtil.createConnection(urlString);
        try {
            connection.connect();
            stringBuffer.append("<br><br>");
            InputStream is = null;

            is = connection.getInputStream();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(is, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                stringBuffer.append(e.getLocalizedMessage() + "<br>");
            }
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                stringBuffer.append(readLine);
            }
            is.close();
            br.close();
            stringBuffer.append("<br>");

        } catch (Exception ex) {
            ex.printStackTrace();
            stringBuffer.append("---903-SocketTimeoutException---");

        } finally {
            if (connection != null) {
                connection = null;
                //connection.disconnect();
            }
        }
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
