package com.fh.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fh.entity.system.User;
import com.fh.util.Const;
/**
 * 
* 类名称：LoginHandlerInterceptor.java
* 类描述： 
* @author FUHANG
* 作者单位： 
* 联系方式：
* 创建时间：2014年6月27日
* @version 1.0
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH) || path.indexOf("erwei/") > 0){
			return true;
		}else{
			HttpSession session = request.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);
			if(user!=null){
				return true;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath() + Const.LOGIN);
				return false;		
				//return true;
			}
		}
	}
	
}
