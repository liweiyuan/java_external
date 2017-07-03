package com.UrlTest.UrlExternalError;

import com.UrlTest.UrlUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

/**
 * Created by admin on 2016/8/19.
 */
public class Error404Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>UrlConnection External Error</h2>");
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/xx";
        URLConnection connection = UrlUtil.createConnection(urlString);
        try {
            connection.connect();
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append(e.getLocalizedMessage() + "<br>");
        } finally {
            if (connection != null) {
                connection = null;
                //connection.disconnect();
            }
        }
        stringBuffer.append("---404 HTTP Error---");
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
