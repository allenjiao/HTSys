package com.gd.websocket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.fh.controller.system.zidian.ZidianService;
import com.fh.extservice.JpTcpService;

/**
 * Demo websocket server
 * 
 * @author tyutNo4<br>
 *         创建时间：2015-1-12下午10:44:06
 */
@ServerEndpoint("/demo")
public class DemoWebSocket {

	private static final Logger log = Logger.getLogger(DemoWebSocket.class);
	private static final AtomicInteger clientIds = new AtomicInteger(0);
	private final int id;
	private Client client;

	private JpTcpService jpTcpService;

	private ZidianService zidianService;

	public DemoWebSocket() {
		id = clientIds.getAndIncrement();
		log.info("id = [ " + id + " ]");
	}

	@OnOpen
	public void onOpen(Session session) {
		// 1.将新连接进来的客户端存到全局Map中
		log.info("onOpen - seesion id = [ " + session.getId() + " ]");
		client = new Client(id, session);
		WebSocketHelper.addClient(client);
	}

	private Map<String, String> ALARM_LEVELs = new HashMap<String, String>();

	@OnMessage
	public void onMessage(Session session, String message) throws Exception {
		log.info("onMessage");

	}

	@OnClose
	public void onClose() {
		log.info("OnClose");
		WebSocketHelper.removeClient(client);
	}

	@OnError
	public void onError(Throwable t) throws Throwable {
		log.info("onError");
		log.error(t);
	}

}
