package com.test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by admin on 2016/8/15.
 */
public class WriteToFileServlet extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        InputStream inputStream=req.getInputStream();
        String realPath = getDocumentRoot(req);
        OutputStream outputStream = null;
        String fileName="UploadFile.txt";
        if(req.getParameter("fileName")!=null){
            fileName=req.getParameter("fileName");
        }
        try {
            // write the inputStream to a FileOutputStream
            outputStream =  new FileOutputStream(new File(realPath,new String(fileName.getBytes("iso-8859-1"), "utf-8")));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            resp.getWriter().write("saved file: "+realPath+" "+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String getDocumentRoot(HttpServletRequest request){
        String webRoot = request.getSession().getServletContext().getRealPath("/");
        if(webRoot == null){
            webRoot = this.getClass().getClassLoader().getResource("/").getPath();
            webRoot = webRoot.substring(0,webRoot.indexOf("WEB-INF"));
        }
        return webRoot;
    }
}
