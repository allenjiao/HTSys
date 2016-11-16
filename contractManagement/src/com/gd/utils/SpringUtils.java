package com.gd.utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringUtils {
	private static ApplicationContext wac = null;

	public static Object getBean(String name, ServletConfig config) {
		setWebApplicationContext(config);
		return wac.getBean(name);
	}

	public static Object getBean(String name, ServletContext context) {
		setWebApplicationContext(context);
		return wac.getBean(name);
	}

	public static Object getBean(String name, HttpServletRequest request) {
		setWebApplicationContext(request);
		return wac.getBean(name);
	}

	public static void setWebApplicationContext(ServletConfig config) {
		if (wac == null) {
			wac = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		}
	}

	public static void setWebApplicationContext(ServletContext context) {
		if (wac == null) {
			wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		}
	}

	public static void setWebApplicationContext(HttpServletRequest request) {
		if (wac == null) {
			wac = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		}
	}

	public static Object getBean(String name) {
		if (wac == null) {
			System.out.println("ApplicationContext没有初始化，在此处手动初始化");
			// 具体该加载哪些配置文件，待研究完源码明确之后再说 XXX classpath:spring/ApplicationContext.xml
			wac = new ClassPathXmlApplicationContext("spring/ApplicationContext.xml");
		}
		return wac.getBean(name);
	}

}
