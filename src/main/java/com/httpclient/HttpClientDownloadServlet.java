package com.httpclient;

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

/**
 * Created by admin on 2016/11/29.
 */
public class HttpClientDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String fileUrl = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>httpClient Download File</h2>");
        if (req.getParameter("httpclientDownload_url") != null) {
            fileUrl = req.getParameter("httpclientDownload_url");
        } else {
            fileUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/readme";
        }
        String path = getDocumentRoot(req);
        String result = downloadFile(fileUrl, path);
        stringBuffer.append(result + "</body></html>");
        resp.getWriter().write(stringBuffer.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    private static final int BUFFER_SIZE = 4096;

    public String downloadFile(String fileURL, String saveDir) {
        FileOutputStream output = null;
        StringBuffer stringBuffer = new StringBuffer();
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(fileURL);

        try {
            HttpResponse response = httpclient.execute(httpget);
            int statusCode=response.getStatusLine().getStatusCode();
            Header[] headers = response.getAllHeaders();
            for (int i = 0; i < headers.length; i++) {
                stringBuffer.append(headers[i].getName() + ":" + headers[i].getValue() + "<br>");
            }
            stringBuffer.append("responseCode:" + statusCode + "<br>");
            if (statusCode == 200) {
                String fileName = "DownloadFile-httpclient";
                HttpEntity entity = response.getEntity();
                InputStream inputStream=new ByteArrayInputStream(EntityUtils.toByteArray(entity));
                String saveFilePath = saveDir + File.separator + fileName;
                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(new File(saveFilePath));

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                stringBuffer.append("<br>File downloaded ! saved path:" + saveDir);
                httpget.abort();

            } else {
                stringBuffer.append("No file to download. Server replied HTTP code: " + statusCode);
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }

        return stringBuffer.toString();
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
