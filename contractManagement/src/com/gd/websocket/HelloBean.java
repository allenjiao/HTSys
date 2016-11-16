package com.gd.websocket;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/hello")
public class HelloBean {

	@OnMessage
	public String  sayHello (String name) {
		System.out.println(name);
		return "Hello " + name;
	}
	
	public HelloBean() {
		System.out.println("构造函数");
	}

	@OnOpen
	public void onOpen(Session session) {
		//1.将新连接进来的客户端存到全局Map中
		System.out.println("open");
	}
}
