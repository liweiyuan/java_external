package com.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by admin on 2016/8/18.
 */
public class ParamServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>this is paramServlet</h2>");
        stringBuffer.append(getAllParams(req));
        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }
    private StringBuffer getAllParams(HttpServletRequest req){
        StringBuffer stringBuffer=new StringBuffer();
        Map<String,String> map = new HashMap();
        Enumeration paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();

            String[] paramValues = req.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    map.put(paramName, paramValue);
                }
            }
        }
        Set<Map.Entry<String, String>> set = map.entrySet();
        for (Map.Entry entry : set) {
            try {
                stringBuffer.append(entry.getKey() + ":" +new String(entry.getValue().toString().getBytes("ISO-8859-1"),"utf-8")+"<br>");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer;
    }
    private StringBuffer getAllHeader(HttpServletRequest req){
        StringBuffer stringBuffer=new StringBuffer();
        Map<String,String> map = new HashMap();
        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();

            String headerValue=req.getHeader(headerName);
            try {
                stringBuffer.append(headerName + ":" +new String(headerValue.toString().getBytes("ISO-8859-1"),"utf-8")+"<br>");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return stringBuffer;
    }
}
