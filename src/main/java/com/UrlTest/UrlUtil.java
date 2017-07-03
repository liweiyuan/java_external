package com.UrlTest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/18.
 */
public class UrlUtil {
    public static HttpURLConnection createConnection(String urlString) throws IOException {
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
    public static String getConnectionInputString(HttpURLConnection connection){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<br><br>");
        InputStream is = null;
        try {
            is = connection.getInputStream();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String readLine = null;
        while ((readLine = br.readLine()) != null) {
            stringBuffer.append(readLine);
        }
        is.close();
        br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuffer.toString()+"<br>";
    }
    public static  String  getConnectionHeader(HttpURLConnection connection){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("<br><br>");
        Map<String, List<String>> map = connection.getHeaderFields();
        for (String key : map.keySet()) {
            stringBuffer.append(key + ":" + map.get(key) + "<br>");
        }
        return stringBuffer.toString()+"<br>";
    }
    public static Map getResponsecodeAndTime(HttpURLConnection connection){
        Long during=null;
        Map map = new HashMap();
        Long  date1=System.currentTimeMillis();
        try {
            int statusCode = connection.getResponseCode();
            map.put("statusCode",statusCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Long  date2=System.currentTimeMillis();
        during=date2-date1;
        map.put("duringTime",during);
        return map;
    }
}
