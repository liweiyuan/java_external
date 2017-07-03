package com.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleHttpServerServlet extends HttpServlet {
    static int httpserver_connectSleeptime, httpserver_responseSleeptime;
    String httpServerStauts = "1";
    static HttpServer server = null;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        httpServerStauts = req.getQueryString();
        if (!httpServerStauts.equals("httpServerStatus=0")) {
            httpserver_connectSleeptime = Integer.parseInt(req.getParameter("httpserver_connectSleeptime"));
            httpserver_responseSleeptime = Integer.parseInt(req.getParameter("httpserver_responseSleeptime"));
            try {
                server = HttpServer.create(new InetSocketAddress(8070), 1);
                server.createContext("/httpserver", new MyHandler());
                server.setExecutor(null); // creates a default executor
                server.start();
                resp.getWriter().write("Server started. connectionSleepTime:" + httpserver_connectSleeptime + "ms , responseSleepTime " + httpserver_responseSleeptime + "ms.");
            } catch (Exception ex) {
                ex.printStackTrace();
                resp.getWriter().write("Server started error,please stop first.");

            }
        } else {
            server.stop(0);
            resp.getWriter().write("Server stoped.");

        }

    }

    static class MyHandler implements HttpHandler {
        public void handle(HttpExchange exchange) throws IOException {

            try {
                Thread.sleep(httpserver_connectSleeptime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String response = "httpServer response!!!!!";
            exchange.sendResponseHeaders(200, response.length());
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/plain");
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            try {
                Thread.sleep(httpserver_responseSleeptime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            os.close();
        }
    }
}

