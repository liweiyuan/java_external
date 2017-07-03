package com.test.quantile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by admin on 2016/11/29.
 */
public class QGetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>HttpClient 调用testQServlet</h2>");
        urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/testq";
        String quantileString="";
        if(req.getParameter("quantileSetting")==null||req.getParameter("quantileSetting").length()==0){
            quantileString="50,75,95,90";
        }else {
            quantileString =  req.getParameter("quantileSetting");
        }
        String[] quantile=quantileString.split(",");
        double[] quartileList=new double[quantile.length];

        for(int i=0;i<quantile.length;i++){
            double x=Double.valueOf(quantile[i])/100;
            quartileList[i]=x;
        }

        int count =1;
        if(req.getParameter("count")!=null){
            count=Integer.parseInt(req.getParameter("count"));
        }
        HttpClient httpclient = new DefaultHttpClient();
        int[] sleeplist = new int[count];
        for (int i = 0; i < count; i++) {

            HttpGet httpget = new HttpGet(urlString);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity,"utf-8");
            sleeplist[i] = Integer.parseInt(new String(responseString));
        }
        httpclient.getConnectionManager().closeExpiredConnections();
        String sleepString="";
        for(int i=0;i<sleeplist.length;i++){
            sleepString+=sleeplist[i]+" , ";
        }
        String qu="";
        for(int i=0;i<quartileList.length;i++){
            qu+=quartileList[i]+" , ";
        }

        String quantileValue=quantileTest(quartileList,sleeplist);
        stringBuffer.append("sleepTime: "+sleepString+"<br>quantileSetting: "+quantileString+" > "+qu+"<br>quantileValue: "+quantileValue);
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    private String quantileTest(double[] quartileList, int[] testdatas) {
        String quantileString="";
        QuantileP2 qp = new QuantileP2(quartileList);
        int[] testdata = testdatas;
        for (int j = 0; j < testdata.length; j++) {
            qp.add(testdata[j]);
        }
        for (double d : qp.markers()) {
            quantileString+=d+" , ";
        }
        return quantileString;
    }

}
