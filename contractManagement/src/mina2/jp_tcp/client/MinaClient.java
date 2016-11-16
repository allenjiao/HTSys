package mina2.jp_tcp.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import mina2.jp_tcp.client.message.ClientMessageHandlerAdapter;
import mina2.jp_tcp.factory.CharsetCodecFactory;
import mina2.jp_tcp.proto.LoginReqOuterClass;
import mina2.jp_tcp.vo.JpTcpVo;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fh.extservice.JpTcpService;
import com.fh.util.Config;
import com.fh.util.SpringUtils;

/**
 * <b>function:</b> mina客户端
 * 
 * @author hoojo
 * @createDate 2012-6-29 下午07:28:45
 * @file MinaClient.java
 * @package com.hoo.mina.client.message
 * @project ApacheMiNa
 * @blog http://blog.csdn.net/IBM_hoojo
 * @email hoojo_@126.com
 * @version 1.0
 */
public class MinaClient {
	private final static Logger log = LoggerFactory.getLogger(MinaClient.class);
	private SocketConnector connector;
	private ConnectFuture future;
	public IoSession session;
	private InetSocketAddress inetSocketAddress;
	private boolean connect;

	public MinaClient(String host, int port) {
		inetSocketAddress = new InetSocketAddress(host, port);
		initialize();
	}

	public MinaClient(int port) {
		inetSocketAddress = new InetSocketAddress(port);
		initialize();
	}

	public boolean initialize() {
		// 创建一个socket连接
		connector = new NioSocketConnector();
		// 设置链接超时时间
		connector.setConnectTimeoutMillis(10000);
		// 获取过滤器链
		DefaultIoFilterChainBuilder filterChain = connector.getFilterChain();
		// 添加编码过滤器 处理乱码、编码问题
		// 创建接收数据的过滤器
		// 设定这个过滤器将以对象为单位读取数据
		// TextLineCodecFactory tcf = new
//		TextLineCodecFactory tcf = new TextLineCodecFactory(Charset.forName("GBK"));
//		ObjectSerializationCodecFactory osc = new ObjectSerializationCodecFactory();
//		ProtocolCodecFilter filter = new ProtocolCodecFilter(tcf);

		ProtocolCodecFilter filter = new ProtocolCodecFilter(new CharsetCodecFactory());
//		LoggingFilter loggingFilter = new LoggingFilter();
//		loggingFilter.setSessionClosedLogLevel(LogLevel.NONE);  
//	    loggingFilter.setSessionCreatedLogLevel(LogLevel.NONE);  
//	    loggingFilter.setSessionOpenedLogLevel(LogLevel.NONE);   
//	    loggingFilter.setMessageReceivedLogLevel(LogLevel.NONE);
//	    loggingFilter.setSessionIdleLogLevel(LogLevel.NONE);
//	    loggingFilter.setMessageSentLogLevel(LogLevel.NONE);
//
//	    filterChain.addLast("logger", loggingFilter);
		filterChain.addLast("objectFilter", filter);
		// 消息核心处理器
		connector.setHandler(new ClientMessageHandlerAdapter());
		// 日志
//		LoggingFilter loggingFilter = new LoggingFilter();
//		loggingFilter.setMessageReceivedLogLevel(LogLevel.INFO);
//		loggingFilter.setMessageSentLogLevel(LogLevel.INFO);
//		filterChain.addLast("loger", loggingFilter);

		Boolean flag = connect();
		// 自动重连
//		new Thread(new ReConnectMinaClient(this)).start();
		return flag;
	}

	public boolean connect() {
		try {
			connect = session.isConnected();
		} catch (Exception e) {
			connect = false;
		}
		if (!connect) {
			try {
				future = connector.connect(inetSocketAddress);
				future.awaitUninterruptibly();// 等待连接创建成功
				session = future.getSession();// 获取会话
				if (session.isConnected()) {
					connect = true;
					log.info("连接[{}:{}]成功", inetSocketAddress.getHostName(), inetSocketAddress.getPort());
					// --------------加登录 START--------------
					LoginReqOuterClass.LoginReq.Builder builder = LoginReqOuterClass.LoginReq.newBuilder();
					builder.setUsername(Config.getStr("tcp_username"));
					builder.setPassword(Config.getStr("tcp_password"));
					builder.setType(Config.getStr("tcp_type"));
					builder.setMac("");
					builder.setRemark("");
					JpTcpVo jpTcpVo = new JpTcpVo();
					jpTcpVo.setM_iDecSystemID(0);
					jpTcpVo.setM_iSourSystemID(0);
					jpTcpVo.setM_iSynchroID(0);
					jpTcpVo.setM_pApplicationID(0);
					jpTcpVo.setM_pBusinessID(1);
					jpTcpVo.setM_pMessageData(builder.build().toByteArray());
					session.write(jpTcpVo);
					// --------------加登录 END--------------
				} else {
					connect = false;
					log.info("连接[{}:{}]失败", inetSocketAddress.getHostName(), inetSocketAddress.getPort());
				}
			} catch (Exception e) {
				connect = false;
				log.error("连接服务器失败:" + e.getMessage());
			}
		}
		return connect;
	}

	public void setAttribute(Object key, Object value) {
		session.setAttribute(key, value);
	}

	public Boolean send(String message) {
		if (connect) {
			try {
				session.write(message);
				return true;
			} catch (Exception e) {
				log.error("session连接异常,发送消息异常：" + message);
				return false;
			}
		} else {
			log.error("session尚未连接,发送消息异常：" + message);
			return false;
		}

	}

	public boolean close() {
		CloseFuture future = session.getCloseFuture();
		future.awaitUninterruptibly(1000);
		connector.dispose();
		return true;
	}

	public static void main(String[] args) throws Exception {
		MinaClient minaClient = new MinaClient("10.131.8.114", 12345);
//		MinaClient minaClient = new MinaClient(3456);
		while (true) {
			JpTcpVo vo = new JpTcpVo();
			vo.setM_iDecSystemID(0);
			vo.setM_pBusinessID(4096);
			vo.setM_iSourSystemID(0);

			vo.setM_pMessageData("h".getBytes(Charset.forName("GBK")));
			minaClient.session.write(vo);
			
			Thread.sleep(5000);
		}
	}
}
