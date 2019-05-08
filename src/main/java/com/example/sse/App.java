package com.example.sse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class App {

    public static void main(String[] args) {
        new App().startWebServer();
    }

    public void startWebServer() {

        final int port = 8080;

        // サーブレットホスト用ハンドラ
        ServletContextHandler sch = new ServletContextHandler(ServletContextHandler.SESSIONS);
        ServletHolder sh = sch.addServlet(SSEServlet.class, "/sse");
        sh.setAsyncSupported(true);

        // 静的ページのホスト用ハンドラ
        final ResourceHandler rh = new ResourceHandler();
        rh.setResourceBase(System.getProperty("user.dir") + "/htdocs");
        rh.setWelcomeFiles(new String[] { "index.html" });
        rh.setCacheControl("no-store,no-cache,must-revalidate");// キャッシュオフ

        final HandlerList hnList = new HandlerList();
        hnList.addHandler(rh);
        hnList.addHandler(sch);

        final Server server = new Server(port);
        server.setHandler(hnList);

        try {
            server.start();
            System.out.println("Server started on port:" + port);
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}