package com.UrlTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


/**
 * Created by admin on 2016/8/17.
 */
public class UrlDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String fileUrl = null;
        stringBuffer.append("<html><head><title></title></head><body><h2>UrlConnection Download File</h2>");
        if (req.getParameter("urlConnectionDownload_url") != null) {
            fileUrl = req.getParameter("urlConnectionDownload_url");
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
        StringBuffer stringBuffer = new StringBuffer();
        HttpURLConnection connection = null;
        try {
            connection = UrlUtil.createConnection(fileURL);
            Map map = UrlUtil.getResponsecodeAndTime(connection);
            int statusCode = (Integer) map.get("statusCode");
            long during = (Long) map.get("duringTime");
            stringBuffer.append("during time is " + during + " .responseCode:" + statusCode + "<br>");
            // always check HTTP response code first
            if (statusCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = connection.getHeaderField("Content-Disposition");
                disposition = "attachment;filename=DownloadFile-UrlConection";
              //  String contentType = connection.getContentType();
                int contentLength = connection.getContentLength();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 9,
                                disposition.length());
                    }
                } else {
                    // extracts file name from URL
                    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                            fileURL.length());
                }
                // stringBuffer.append("<br>Content-Type = " + contentType);
                // stringBuffer.append("<br>Content-Disposition = " + disposition);
                stringBuffer.append("<br>Content-Length = " + contentLength);
                // stringBuffer.append("<br>fileName = " + fileName);

                // opens input stream from the HTTP connection
                InputStream inputStream = connection.getInputStream();
                String saveFilePath = saveDir + File.separator + fileName;
                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(new File(saveFilePath));

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.close();
                inputStream.close();
                stringBuffer.append("<br>File downloaded ! saved path:" + saveDir);
            } else {
                stringBuffer.append("No file to download. Server replied HTTP code: " + statusCode);
            }
            stringBuffer.append(UrlUtil.getConnectionHeader(connection));
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
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
