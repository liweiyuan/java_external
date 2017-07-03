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
public class QGetServlet2 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long startTime=System.currentTimeMillis();
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = null;
        String urlString2 = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>HttpClient 调用testQServlet2</h2>");
        urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/testq";
        urlString2 = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/testq2";
        String quantileString =  req.getParameter("quantileSetting2");
        String[] quantile=quantileString.split(",");
        double[] quartileList=new double[quantile.length];

        for(int i=0;i<quantile.length;i++){
            double x=Double.valueOf(quantile[i])/100;
            quartileList[i]=x;
        }

        int count =1;
        if(req.getParameter("count2")!=null){
            count=Integer.parseInt(req.getParameter("count2"));
        }

        HttpClient httpclient = new DefaultHttpClient();
        int[] sleeplist = new int[count];
        int[] sleeplist2 = new int[count];
        for (int i = 0; i < count; i++) {
            HttpGet httpget = new HttpGet(urlString);
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity,"utf-8");
            sleeplist[i] = Integer.parseInt(new String(responseString));
            httpget.abort();

            HttpGet httpget2 = new HttpGet(urlString2);
            HttpResponse response2 = httpclient.execute(httpget2);
            HttpEntity entity2 = response2.getEntity();
            String responseString2 = EntityUtils.toString(entity2,"utf-8");
            sleeplist2[i] = Integer.parseInt(new String(responseString2));
            httpget2.abort();
        }
        String sleepString="";
        String sleepString2="";
        for(int i=0;i<sleeplist.length;i++){
            sleepString+=sleeplist[i]+" , ";
        }
        for(int i=0;i<sleeplist2.length;i++){
            sleepString2+=sleeplist2[i]+" , ";
        }
        String qu="";
        for(int i=0;i<quartileList.length;i++){
            qu+=quartileList[i]+" , ";
        }
        long endTime=System.currentTimeMillis();
        int[] duration=new int[2];
        System.out.println("durationwebaction"+(endTime-startTime));
        duration[0]= (int) (endTime-startTime);

        int[][] testdatas= new int[][]{sleeplist,sleeplist2,duration};
        String quantileValue=quantileTest(quartileList,sleeplist);
        String quantileValue2=quantileTest(quartileList,sleeplist2);

        String quantileValueResult=quantileTest2(quartileList,testdatas);

        stringBuffer.append("sleepTime:webaction1: "+sleepString+"webaciton2:"+sleepString2+"<br>quantileSetting: "
                +quantileString+" > "+qu+"<br>webaction1's quantileValue: "+quantileValue+"webaction2's quantileValue:"+quantileValue2+
                "<br>webaction merge  quantileValue: "+quantileValueResult);
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());

    }

    private String quantileTest(double[] quartileList, int[] testdatas) {
        QuantileP2 qm=new QuantileP2(quartileList);
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
    private String quantileTest2(double[] quartileList, int[][] testdatas) {
        QuantileP2 qm=new QuantileP2(quartileList);
        String quantileString="";
        for(int i=0;i<testdatas.length;i++){
            QuantileP2 qp=new QuantileP2(quartileList);
            int[] testdata=testdatas[i];
            for(int j=0;j<testdata.length;j++) {
                qp.add(testdata[j]);
            }
            for (double d : qp.markers()) {
                System.out.print(d + "\t");
            }
            System.out.println();
           qm.merge(qp);
        }
        for(double i:qm.getMarkers_Y()){
            System.out.println("ddd:"+i+"\t");
            quantileString+=i+" , ";
        }
        for(double x:qm.getMarkers_X()){
            System.out.println("X_quantile:"+x+"\t");
        }
        return quantileString;
    }

}
