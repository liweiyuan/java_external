package com.ning.asynchttpclient;

import java.io.IOException;
import java.net.URLDecoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClient.BoundRequestBuilder;
import com.ning.http.client.FluentCaseInsensitiveStringsMap;
import com.ning.http.client.Response;
public class AsyncHttpClientPostServlet extends HttpServlet{
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        doGet(request, response);
	 }
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		 resp.setContentType("text/html;charset=UTF-8");
	     StringBuffer stringBuffer = new StringBuffer();
	     String urlString = null;
	     stringBuffer.append("<html><head><title></title></head><body><h2>AsyncHttpClient</h2>");
	     if (req.getParameter("asynchttpclientPostUrl") == null) {
	         urlString = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath() + "/test";
	     } else {
	         urlString = (String) req.getParameter("asynchttpclientPostUrl");
	         String queryString = req.getQueryString();
	         urlString = URLDecoder.decode(req.getQueryString().toString().substring(queryString.indexOf("=") + 1), "UTF-8");
	         if (!urlString.contains("http")) {
	             String urlpre = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
	             urlString = urlpre + "/" + urlString;
	         }
	     }
	     stringBuffer.append(urlString + "<br>");
         AsyncHttpClient asyncHttpClient=new AsyncHttpClient();  
         BoundRequestBuilder  builder=asyncHttpClient.preparePost(urlString);
	     Future<Response> responseFuture = builder.execute();
	     Response response;
		 try {
			response = responseFuture.get();
			int statusCode=response.getStatusCode();
//		     FluentCaseInsensitiveStringsMap headers = response.getHeaders();
//		     while(headers.iterator().hasNext()) {
//		        Header header = (Header) headers.iterator().next();
//		        stringBuffer.append(header+"<br>");
//	         }
//		     stringBuffer.append("responseCode:" + statusCode + "<br>");
		     if (statusCode == 200) {
		           String responseBody=response.getResponseBody();
		           stringBuffer.append("<br>"+responseBody+"<br>");
		     }
		 } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 } catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
	     stringBuffer.append(" </body></html>");
	     resp.getWriter().write(stringBuffer.toString());
		
	}
}
