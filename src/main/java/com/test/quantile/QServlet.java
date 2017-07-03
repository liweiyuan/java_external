package com.test.quantile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by admin on 2016/11/29.
 */
public class QServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        StringBuffer stringBuffer = new StringBuffer();
        String urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/q";
        stringBuffer.append("<html><head><title></title></head><body><h2>分位值计算</h2>");
        try {
            String quantileString = req.getParameter("quantileSetting");
            String[] quantile = quantileString.split(",");
            double[] quartileList = new double[quantile.length];
            for (int i = 0; i < quantile.length; i++) {
                double x = Double.valueOf(quantile[i]) / 100;
                quartileList[i] = x;
            }

            String dataString = req.getParameter("dataString");
            String[] data = dataString.split(",");
            double[] dataList = new double[data.length];
            for (int i = 0; i < data.length; i++) {
                double x = Double.valueOf(data[i]);
                dataList[i] = x;
            }
            String quantileValue = quantileTest(quartileList, dataList);
            stringBuffer.append("data: " + dataString + "<br>quantileSetting: " + quantileString  + "<br>quantileValue: " + quantileValue);
        } catch (Exception e) {
            e.printStackTrace();
            stringBuffer.append("请设置测试数据，及分位数设置，如!<br>调用链接：<br>"+urlString+"?quantileSetting=75,85,90,99&dataString=6,80,900,1500,6000");
        }
        stringBuffer.append(" </body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    private String quantileTest(double[] quartileList, double[] testdatas) {
        String quantileString = "";
        QuantileP2 qp = new QuantileP2(quartileList);
        double[] testdata = testdatas;
        for (int j = 0; j < testdata.length; j++) {
            qp.add(testdata[j]);
        }
        for (double d : qp.markers()) {
            quantileString += d + " , ";
        }
        return quantileString;
    }

}
