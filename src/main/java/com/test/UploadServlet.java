package com.test;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by admin on 2016/8/12.
 */
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
        resp.getWriter().write("finish");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
        resp.getWriter().write("finish");

    }

    public void process(HttpServletRequest req, HttpServletResponse resp){
        String realPath = req.getSession().getServletContext().getRealPath("/");
        DiskFileItemFactory factory=new DiskFileItemFactory();
        factory.setSizeThreshold(4096);
        factory.setRepository(new File(realPath));
        ServletFileUpload upload=new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        List<FileItem> items=null;
        String fileName="";
        try{
            items=upload.parseRequest(req);
            for(FileItem item :items){
            fileName=getName(item.getName());
                File myfile=new File(realPath+fileName);
                if(!myfile.isDirectory()){
                    item.write(myfile);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //过滤路径部分
   public static String getName(String beforeProcess){
        String afterProcess="";
        int beginIndex=0;
        if(beforeProcess!=null){
            beginIndex=beforeProcess.lastIndexOf("\\");
            afterProcess=beforeProcess.substring(beginIndex+1);
        }
        return afterProcess;
    }
}
