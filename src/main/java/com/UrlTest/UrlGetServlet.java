package com.UrlTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.Map;

/**
 * Created by admin on 2016/8/12.
 */
public class UrlGetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title><style type=\"text/css\">\n" +
                "p.one\n" +
                "{\n" +
                "border-style: solid;\n" +
                "border-color: #0000ff\n" +
                "}" +
                "</style></head><body><h2>UrlConnection 调用</h2>");
        int count = 0;
        if(req.getParameter("count")==null||req.getParameter("count")==""){
            count=1;
        }
        if(req.getParameter("count")!=null&&req.getParameter("count")!=""){
            count=Integer.parseInt(req.getParameter("count"));
        }
        int[] sleeplist = new int[count];
        if (req.getParameter("urlConnectUrl") == null) {
            urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/test";
        } else {
            urlString = (String) req.getParameter("urlConnectUrl");
            //String queryString=req.getQueryString();
            urlString=URLDecoder.decode(urlString,"UTF-8");
            if (!urlString.contains("http")) {
                String urlpre = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
                urlString = urlpre + "/" + urlString;
            }
        }
        stringBuffer.append(urlString+"<br>");
        HttpURLConnection connection = null;

            for(int i =0;i<count;i++) {
                try {
                    connection = UrlUtil.createConnection(urlString);
                    connection.setRequestMethod("GET");
                    connection.connect();
                    Map map = UrlUtil.getResponsecodeAndTime(connection);
                    int statusCode = (Integer) map.get("statusCode");
                    long during = (Long) map.get("duringTime");
                    stringBuffer.append("<p class=\"one\">"+"during time is " + during + " .responseCode:" + statusCode + "<br>");
                    stringBuffer.append(UrlUtil.getConnectionHeader(connection)+"</p>");
                    sleeplist[i]= (int) during;
                  /*  if (statusCode == 200) {
                        stringBuffer.append(UrlUtil.getConnectionInputString(connection)+"</p>");
                    }*/
                }
                //return response;
                catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        String sleepString="";
        for(int i=0;i<sleeplist.length;i++){
            sleepString+=sleeplist[i]+" , ";
        }
        long endTime = System.currentTimeMillis();
        long duringAll=endTime-startTime;
        stringBuffer.append("<b>All the webactions time: "+duringAll+"<b><br>");
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
