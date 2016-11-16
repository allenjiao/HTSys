package com.gd.websocket;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.Session;

/**
 * 
 * @author tyutNo4<br>
 * 创建时间：2015-1-12下午10:56:32
 */
public class Client {

    private final int id;
    private final Session session;

    public Client(int id, Session session) {
        this.id = id;
        this.session = session;
    }

    protected void sendMessage(String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException ioe) {
            CloseReason cr = new CloseReason(CloseCodes.CLOSED_ABNORMALLY, ioe.getMessage());
            try {
                session.close(cr);
            } catch (IOException ioe2) {
                // Ignore
            }
        }
    }

    public int getId() {
        return id;
    }

}
