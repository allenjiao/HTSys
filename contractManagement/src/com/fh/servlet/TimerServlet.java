package com.fh.servlet;

import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import mina2.jp_tcp.client.MinaClient;

import org.apache.log4j.Logger;

import com.fh.extservice.JpTcpService;
import com.fh.util.Config;
import com.fh.util.SpringUtils;

/**
 * 随系统启动的自定任务预约servlet
 * 
 * @author rr
 * @dateTime 2014-6-13上午11:16:29
 */
@SuppressWarnings("serial")
public class TimerServlet extends HttpServlet implements ServletContextListener {
	private static final Logger log = Logger.getLogger(TimerServlet.class);
	private static Timer timer = null;

	public static MinaClient minaClient = null;

	/**
	 * Constructor of the object.
	 */
	public TimerServlet() {
		super();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		timer.cancel();
//		event.getServletContext().log("定时器销毁");
		log.info("定时器销毁");
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		timer = new java.util.Timer();
//		minaClient = new MinaClient(Config.getStr("tcp_ip"), Integer.parseInt(Config.getStr("tcp_port")));
//		event.getServletContext().log("定时器已启动");
		log.info("定时器已启动");
		// 任务1:
//		timer.schedule(new JpTcpConnectAndLogin(), 10000);
//		timer.schedule(new Jle(new JpTcpConnectAndLogin(), 10000);
//		timer.schedule(new JpTcpHeart(), 12000, 9 * 1000);
//		timer.schedupTcpHeart(), 12000, 9 * 1000);
//		timer.schedule(new LietouDiandu(), 20000, 60 * 1000);
		// 更多任务...
//		event.getServletContext().log("已经添加任务");
		log.info("已经添加任务");
	}

	/**
	 * 发送tcp-socket心跳任务类
	 * 
	 * @author reny<br>
	 *         创建时间：2015-4-1下午5:03:49
	 */
	class JpTcpHeart extends TimerTask {
		private JpTcpService jpTcpService = null;

		@Override
		public void run() {
			if (jpTcpService == null) {
				jpTcpService = (JpTcpService) SpringUtils.getBeanByClass(JpTcpService.class);
			}
			// 如果连接正常则发送心跳，否则重连
			try {
				if (TimerServlet.minaClient != null && TimerServlet.minaClient.connect()) {
					jpTcpService.sentHeart();
				}
			} catch (Exception e) {
				log.error(e);
			}
		}
	}

	/**
	 * 连接/登录
	 * 
	 * @author reny<br>
	 *         创建时间：2015-4-16下午12:00:43
	 */
	class JpTcpConnectAndLogin extends TimerTask {
		@Override
		public void run() {
			minaClient = new MinaClient(Config.getStr("tcp_ip"), Integer.parseInt(Config.getStr("tcp_port")));
		}
	}

}
