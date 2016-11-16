package com.fh.filter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.fh.controller.base.BaseController;
import com.fh.util.FileUtil;

/**
 * 登录验证过滤器

 * 创建人：fuhang 
 * 创建时间：2014年2月17日
 * @version
 */
public class startFilter extends BaseController implements Filter{

	
	
	
	/**
	 * 初始化
	 */
	public void init(FilterConfig fc) throws ServletException {
		//FileUtil.createDir("d:/CarHpSysPicture/topic/");
		//writeFile();
	}
	
	
	public  void  writeFile(){
		//String xmpath = String.valueOf(Thread.currentThread().getContextClassLoader().getResource(""))+"../../";	//项目路径
		FileUtil.createDir("d:/timer/");
		String xmpath = "d:/";	
		xmpath = xmpath + "timer/of.txt";
		//xmpath = xmpath.substring(6);					//去掉 'file:/'
		PrintWriter pw;
		try {
			pw = new PrintWriter( new FileWriter(xmpath));
			pw.print("off");
	        pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//计时器
	public void timer() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 14); // 控制时
		calendar.set(Calendar.MINUTE, 04); 		// 控制分
		calendar.set(Calendar.SECOND, 0); 		// 控制秒

		Date time = calendar.getTime(); 		// 得出执行任务的时间

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				
				//PersonService personService = (PersonService)ApplicationContext.getBean("personService");

				
				//System.out.println("-------设定要指定任务--------");
			}
		}, time, 3000);// 这里设定将延时每天固定执行
	}


	public void destroy() {
		// TODO Auto-generated method stub
		
	}


	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
	
	
}
