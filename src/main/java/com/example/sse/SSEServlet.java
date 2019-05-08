package com.example.sse;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.riversun.sse.SSEHelper;
import org.riversun.sse.SSEHelper.EventTarget;

/**
 * アクセスしてきたクライアント全員に
 * サーバーPUSH(SSE:Server Sent Events)を送信するサーブレット
 */
@SuppressWarnings("serial")
public class SSEServlet extends HttpServlet {

    private final SSEHelper mPushHelper = new SSEHelper();

    /**
     * (JSから)GETリクエストがあったら、リクエスト元をターゲットに追加する
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Added " + req + " to target to broadcast");

        final EventTarget tgt = new EventTarget(req);
        mPushHelper.addTarget(tgt);
    }

    /**
     * POSTリクエストがあったら、登録されたターゲットにメッセージをブロードキャストする
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msgToSend = req.getParameter("message");
        if (msgToSend == null) {
            msgToSend = "";
        }
        mPushHelper.broadcast("message", msgToSend);

        resp.setStatus(HttpServletResponse.SC_OK);
        final PrintWriter out = resp.getWriter();
        out.close();
    }

}
