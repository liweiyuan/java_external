package com.httpclient;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.Header;
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
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by admin on 2016/11/29.
 */
public class HttpClientUploadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<html><head><title></title></head><body><h2>UrlConnection Upload File</h2>");
        String urlpre = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String urlString = urlpre + "/writeToFile";
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(4096);
        //String realPath = req.getSession().getServletContext().getRealPath("/");
        // String realPath = this.getClass().getClassLoader().getResource("/").getPath();
        String realPath = getDocumentRoot(req);
        factory.setRepository(new File(realPath));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items = null;
        String fileName = "";
        StringBuilder stringBuilder = null;
        BufferedReader reader = null;
        stringBuilder = new StringBuilder();
        if (req.getHeader("content-type") != null) {
            if (req.getHeader("content-type").contains("multipart/form-data")) {
                try {
                    items = upload.parseRequest(req);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
                FileItem item = items.get(0);
                fileName = item.getName();
                reader = new BufferedReader(new BufferedReader(new InputStreamReader(item.getInputStream())));
            }
        } else {
            String defaultFileName = getDocumentRoot(req) + "/readme";
            File defaultfile = new File(defaultFileName);
            reader = new BufferedReader(new BufferedReader(new FileReader(defaultfile)));
            fileName = "uploadDefaultFile-httpclient";
        }
        String line = null;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }

        String filecontent = stringBuilder.toString();
        urlString += "?fileName=" + URLEncoder.encode(fileName, "UTF-8");
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(urlString);
        HttpResponse response = httpclient.execute(httpget);
        int statusCode=response.getStatusLine().getStatusCode();

        Header[] headers = response.getAllHeaders();
        for (int i=0; i<headers.length; i++) {
            stringBuffer.append(headers[i]+"<br>");
        }
        stringBuffer.append("responseCode:" + statusCode + "<br>");
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String responseString= EntityUtils.toString(entity);
            stringBuffer.append("<br>"+responseString+"<br>");
        }

        stringBuffer.append("</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }
    public String getDocumentRoot(HttpServletRequest request) {
        String webRoot = request.getSession().getServletContext().getRealPath("/");
        if (webRoot == null) {
            webRoot = this.getClass().getClassLoader().getResource("/").getPath();
            webRoot = webRoot.substring(0, webRoot.indexOf("WEB-INF"));
        }
        return webRoot;
    }
}
