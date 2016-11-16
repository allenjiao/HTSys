package com.fh.controller.system.login;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.menu.MenuService;
import com.fh.controller.system.role.RoleService;
import com.fh.controller.system.user.UserService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

@Controller
public class LoginController extends BaseController {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "menuService")
	private MenuService menuService;

	/*
	 * ==================================================权限======================
	 * ====================================
	 */
	@Resource(name = "roleService")
	private RoleService roleService;

	/**
	 * 获取用户权限
	 */
	public Map<String, String> getUQX(HttpSession session) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String USERNAME = session.getAttribute("USERNAME").toString();
			pd.put("USERNAME", USERNAME);
			String ROLE_ID = userService.findByUId(pd).get("ROLE_ID")
					.toString();// 通过USERNAME获取角色ID

			pd.put("ROLE_ID", ROLE_ID);

			PageData pd2 = new PageData();
			pd2.put("USERNAME", USERNAME);
			pd2.put("ROLE_ID", ROLE_ID);

			pd = roleService.findObjectById(pd);// 获取角色对象

			if ("4".equals(pd.getString("PARENT_ID"))) {// 判断其父id是不是等于5()
				pd2 = roleService.findGLbyrid(pd2);
				map.put("FX_QX", pd2.get("FX_QX").toString());
				map.put("FW_QX", pd2.get("FW_QX").toString());
				map.put("QX1", pd2.get("QX1").toString());
				map.put("QX2", pd2.get("QX2").toString());
				map.put("QX3", pd2.get("QX3").toString());
				map.put("QX4", pd2.get("QX4").toString());
				map.put("nokf", "1");
			} else {
				map.put("nokf", "0");// 判断是不是管理
			}

			if ("6".equals(pd.getString("PARENT_ID"))) {// 判断其父id是不是等于
				pd2 = roleService.findYHbyrid(pd2);
				map.put("C1", pd2.get("C1").toString());
				map.put("C2", pd2.get("C2").toString());
				map.put("C3", pd2.get("C3").toString());
				map.put("C4", pd2.get("C4").toString());
				map.put("Q1", pd2.get("Q1").toString());
				map.put("Q2", pd2.get("Q2").toString());
				map.put("Q3", pd2.get("Q3").toString());
				map.put("Q4", pd2.get("Q4").toString());
				map.put("noG", "1");
			} else {
				map.put("noG", "0");// 判断是不是普通用户
			}

			map.put("add", pd.getString("ADD_QX"));
			map.put("del", pd.getString("DEL_QX"));
			map.put("edit", pd.getString("EDIT_QX"));
			map.put("cha", pd.getString("CHA_QX"));

			this.getRemortIP(USERNAME);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return map;
	}

	/*
	 * ==================================================登录过滤====================
	 * ======================================
	 */

	/*
	 * ==================================================登录过滤====================
	 * ======================================
	 */

	/**
	 * 获取登录用户的IP
	 * 
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {
			ip = request.getRemoteAddr();
		} else {
			ip = request.getHeader("x-forwarded-for");
		}
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}

	// private User user;

	/**
	 * 访问登录页
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login_toLogin")
	public ModelAndView toLogin() throws Exception {
		mv.clear();
		pd = this.getPageData();
		mv.setViewName("system/admin/login");
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 请求登录，验证用户
	 */
	@RequestMapping(value = "/login_login")
	public ModelAndView login(HttpSession session) throws Exception {
		mv.clear();
		String sessionCode = (String) session
				.getAttribute(Const.SESSION_SECURITY_CODE);
		String errInfo = "";

		pd = this.getPageData();
		PageData pdm = new PageData();
		pdm = this.getPageData();

		String code = pd.getString("code");

		if (null == code || "".equals(code)) {
			mv.setViewName("redirect:/");
		} else {

			String USERNAME = pd.get("loginname").toString();
			String PASSWORD = pd.get("password").toString();
			pd.put("USERNAME", USERNAME);
			if (Tools.notEmpty(sessionCode)
					&& sessionCode.equalsIgnoreCase(code)) {
				String passwd = MD5.md5(PASSWORD);
				pd.put("PASSWORD", passwd);
				pd = userService.getUserByNameAndPwd(pd);
				if (pd != null) {
					pd.put("LAST_LOGIN", new java.text.SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss").format(new Date())
							.toString());
					userService.updateLastLogin(pd);

					User user = userService.getUserAndRoleById(pd
							.getString("USER_ID"));

					// user.setUSER_ID(pd.getString("USER_ID"));
					user.setUSERNAME(pd.getString("USERNAME"));
					user.setPASSWORD(pd.getString("PASSWORD"));
					user.setNAME(pd.getString("NAME"));
					user.setRIGHTS(pd.getString("RIGHTS"));
					user.setROLE_ID(pd.getString("ROLE_ID"));
					user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
					user.setIP(pd.getString("IP"));
					user.setSTATUS(pd.getString("STATUS"));

					// 避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
					session.setAttribute(Const.SESSION_USER, user);
					session.removeAttribute(Const.SESSION_SECURITY_CODE);
					session.setAttribute(Const.SESSION_ROLE_RIGHTS, user
							.getRole().getRIGHTS());// 将角色权限存入session
					session.setAttribute(Const.SESSION_USER_RIGHTS,
							user.getRIGHTS());// 将用户权限存入session
					session.setAttribute("USERNAME", user.getUSERNAME());
					session.setAttribute("QX", this.getUQX(session));
				} else {
					errInfo = "用户名或密码有误！";
				}
			} else {
				errInfo = "验证码输入有误！";
			}
			if (Tools.isEmpty(errInfo)) {
				// mv.setViewName("redirect:resource/listResource.do");
				mv.setViewName("redirect:login_index.do");
			} else {
				mv.addObject("errInfo", errInfo);
				mv.addObject("loginname", USERNAME);
				mv.addObject("password", PASSWORD);
				mv.setViewName("system/admin/login");
			}
			mv.addObject("pd", pdm);
		}
		return mv;
	}

	/**
	 * 访问系统首页
	 */
	@RequestMapping(value = "/login_index")
	public ModelAndView login_index(HttpSession session, Page page) {
		pd = this.getPageData();
		this.setPdmenu(pd);
		try {
			User user = (User) session.getAttribute(Const.SESSION_USER);
			if (user != null) {
				user = userService.getUserAndRoleById(user.getUSER_ID());
				Role role = user.getRole();
				String roleRights = role != null ? role.getRIGHTS() : "";
				String userRights = user.getRIGHTS();
				// 避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
				session.setAttribute(Const.SESSION_ROLE_RIGHTS, roleRights); // 将角色权限存入session
				session.setAttribute(Const.SESSION_USER_RIGHTS, userRights); // 将用户权限存入session
				session.setAttribute("USERNAME", user.getUSERNAME());

				List<Menu> menuList = menuService.listAllMenu();
				if (Tools.notEmpty(userRights) || Tools.notEmpty(roleRights)) {
					for (Menu menu : menuList) {
						menu.setHasMenu(RightsHelper.testRights(userRights,
								menu.getMENU_ID())
								|| RightsHelper.testRights(roleRights,
										menu.getMENU_ID()));
						if (menu.isHasMenu()) {
							List<Menu> subMenuList = menu.getSubMenu();
							for (Menu sub : subMenuList) {
								sub.setHasMenu(RightsHelper.testRights(
										userRights, sub.getMENU_ID())
										|| RightsHelper.testRights(roleRights,
												sub.getMENU_ID()));
							}
						}
					}
				}

				session.setAttribute("QX", this.getUQX(session)); // 权限放到session中

				/*
				 * String USERNAME =
				 * session.getAttribute("USERNAME").toString();
				 * //获取当前登录者loginname pd.put("USERNAME", USERNAME); String NAME
				 * = userService.findByUId(pd).get("NAME").toString();
				 * //通过USERNAME获取name mv.addObject("NAME", NAME);
				 */

				// FusionCharts
				// String strXML =
				// "<graph caption='前12个月订单销量柱状图' xAxisName='月份' yAxisName='值' decimalPrecision='0' formatNumberScale='0'>"
				// +
				// "<set name='2013-05' value='4' color='AFD8F8'/><set name='2013-04' value='0' color='AFD8F8'/>"
				// +
				// "<set name='2013-03' value='0' color='AFD8F8'/><set name='2013-02' value='0' color='AFD8F8'/>"
				// +
				// "<set name='2013-01' value='0' color='AFD8F8'/><set name='2012-01' value='0' color='AFD8F8'/>"
				// +
				// "<set name='2012-11' value='0' color='AFD8F8'/><set name='2012-10' value='0' color='AFD8F8'/>"
				// +
				// "<set name='2012-09' value='0' color='AFD8F8'/><set name='2012-08' value='0' color='AFD8F8'/>"
				// +
				// "<set name='2012-07' value='0' color='AFD8F8'/><set name='2012-06' value='0' color='AFD8F8'/></graph>";
				// mv.addObject("strXML", strXML);

				mv.setViewName("system/admin/index");
				mv.addObject("user", user);
				mv.addObject("menuList", menuList);
			} else {
				mv.setViewName("system/admin/login");// session失效后跳转登录页面
			}

		} catch (Exception e) {
			mv.setViewName("system/admin/login");
			logger.error(e.getMessage(), e);
		}
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 进入首页后的默认页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login_default")
	public String defaultPage() {
		return "system/admin/default";
	}

	/**
	 * 用户注销
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout")
	public ModelAndView logout(HttpSession session) {
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(Const.SESSION_USER_RIGHTS);
		pd = this.getPageData();
		String msg = pd.getString("msg");
		pd.put("msg", msg);
		/*
		 * mv.setViewName("login_toLogin.do"); pd.put("msg", msg);
		 * mv.addObject("pd",pd); mv.addObject("msg",msg);
		 */
		mv.setViewName("system/admin/login");
		mv.addObject("pd", pd);
		return mv;
	}

}
