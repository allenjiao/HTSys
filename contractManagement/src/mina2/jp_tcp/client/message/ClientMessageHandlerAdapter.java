package mina2.jp_tcp.client.message;

import mina2.jp_tcp.vo.JpTcpVo;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fh.extservice.JpTcpService;
import com.fh.util.SpringUtils;

/**
 * <b>function:</b> 客户端消息处理类
 * 
 * @author hoojo
 * @createDate 2012-6-29 下午07:24:22
 * @file ClientMessageHandlerAdapter.java
 * @package com.hoo.mina.client.message
 * @project ApacheMiNa
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class ClientMessageHandlerAdapter extends IoHandlerAdapter {

	private final static Logger log = LoggerFactory.getLogger(ClientMessageHandlerAdapter.class);
	private static JpTcpService jpTcpService = null;

	public ClientMessageHandlerAdapter() {
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		log.info("incomming client..." + session.getRemoteAddress());
//		session.write("你好服务器");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("client disconnect..." + session.getRemoteAddress());
	}

	public void messageReceived(final IoSession session, Object message) throws Exception {
		if (jpTcpService == null) {
			jpTcpService = (JpTcpService) SpringUtils.getBeanByClass(JpTcpService.class);
		}
//        String content = message.toString();
		// 添加自己的解析消息的实现方法
		final JpTcpVo in = (JpTcpVo) message;
		log.debug("收到消息: " + in.getM_pBusinessID());
		new Thread() {
			@Override
			public void run() {
				JpTcpVo out = null;
				try {
					out = jpTcpService.getTcpMessage(in);
				} catch (Exception e) {
					log.error("socket消息处理错误：" + e.getMessage());
				}
				if (out != null) {
					session.write(out);
				}
			}
		}.start();
	}

	public void messageSent(IoSession session, Object message) throws Exception {
//		log.info("messageSent 客户端发送消息：" + message);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		log.info("服务器发生异常： {}", cause.getMessage());
		session.close(true);
	}

}
