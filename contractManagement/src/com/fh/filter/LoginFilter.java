package com.fh.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.FileUtil;

/**
 * 登录验证过滤器
 */
public class LoginFilter extends BaseController implements Filter {

	/**
	 * 初始化
	 */
	public void init(FilterConfig fc) throws ServletException {
		FileUtil.createDir("d:/CarHpSysPicture/topic/");
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		String url = request.getRequestURL().toString();
		if (url.indexOf("erwei/listErwei.do") >= 0) {//如果是普通页面（比如二维展示页面）则不需要跳转至登录页面
			
		} else {
			HttpSession session = request.getSession(false);
			User user = (User)session.getAttribute(Const.SESSION_USER);
			if (user == null) {
				mv.setViewName("system/admin/login");//session失效后跳转登录页面
				return;
			} 
		}
		chain.doFilter(req, res); // 调用下一过滤器
	}

}
