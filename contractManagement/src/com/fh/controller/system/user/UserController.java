package com.fh.controller.system.user;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;







import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.menu.MenuService;
import com.fh.controller.system.organ.OrganService;
import com.fh.controller.system.role.RoleService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Organ;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.util.Const;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

/** 
 * 类名称：UserController
 * 创建人：fuhang 
 * 创建时间：2014年6月28日
 * @version
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {
	
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="organService")
	private OrganService organService;
	
	//===================================================================================================
	
	
	/* ===============================权限================================== */
	public void getQx(){
		HttpSession session = this.getRequest().getSession();
		Map<String, Integer> map = (Map<String, Integer>)session.getAttribute("QX");
		mv.addObject("QX",map);
	}
	/* ===============================权限================================== */
	
	
	
	/**
	 * 保存用户
	 */
	@RequestMapping(value="/saveU")
	public ModelAndView saveU(PrintWriter out) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		pd.put("USER_ID", this.get32UUID());	//ID
		pd.put("RIGHTS", "");					//权限
		pd.put("LAST_LOGIN", "");				//最后登录时间
		pd.put("IP", "");						//IP
		pd.put("STATUS", "0");					//状态
		
		pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")));
		
		if(userService.findByUId(pd) != null){
			mv.addObject("msg","此用户名已存在!");
		}else{
			userService.saveU(pd);
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 判断用户名是否存在
	 */
	@RequestMapping(value="/hasU")
	public void hasU(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			if(userService.findByUId(pd) != null){
				out.write("error");
			}else{
				out.write("success");
			}
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping(value="/editU")
	public ModelAndView editU(PrintWriter out) throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		if(pd.getString("PASSWORD") != null && !"".equals(pd.getString("PASSWORD"))){
			pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")));
		}
		
		userService.editU(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	/**
	 * 去修改用户页面
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		mv.clear();
		pd = this.getPageData();
		
		//顶部修改个人资料
		String fx = pd.getString("fx");
		
		//System.out.println(fx);
		
		if("head".equals(fx)){
			mv.addObject("fx", "head");
		}else{
			mv.addObject("fx", "user");
		}
		
		List<Organ> organs = organService.listTreeOrgan("0");
//		List<Role> roleList = roleService.listAllERRoles();			//列出所有二级角色
		pd = userService.findByUiId(pd);							//根据ID读取
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "editU");
		mv.addObject("pd", pd);
		mv.addObject("organList", organs);
		mv.addObject("roleList", roleList);
		
		return mv;
	}
	
	/**
	 * 去新增用户页面
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		mv.clear();
		pd = this.getPageData();
//		List<Role> roleList;
//		
//		roleList = roleService.listAllERRoles();			//列出所有二级角色
		List<Organ> organs = organService.listTreeOrgan("0");
		
		mv.setViewName("system/user/user_edit");
		mv.addObject("msg", "saveU");
		mv.addObject("pd", pd);
		mv.addObject("organList", organs);
		mv.addObject("roleList", roleList);

		return mv;
	}

	List<Role> roleList;// 列出所有‘可用的’二级角色
	/**
	 * 显示用户列表(用户组)
	 */
	@RequestMapping(value = "/listUsers")
	public ModelAndView listUsers(HttpSession session, Page page) throws Exception {
		mv.clear();
		this.getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		String USERNAME = pd.getString("USERNAME");

		if (null != USERNAME && !"".equals(USERNAME)) {
			USERNAME = USERNAME.trim();
			pd.put("USERNAME", USERNAME);
		}

		String lastLoginStart = pd.getString("lastLoginStart");
		String lastLoginEnd = pd.getString("lastLoginEnd");

		if (lastLoginStart != null && !"".equals(lastLoginStart)) {
			lastLoginStart = lastLoginStart + " 00:00:00";
			pd.put("lastLoginStart", lastLoginStart);
		}
		if (lastLoginEnd != null && !"".equals(lastLoginEnd)) {
			lastLoginEnd = lastLoginEnd + " 00:00:00";
			pd.put("lastLoginEnd", lastLoginEnd);
		}

		page.setPd(pd);
		List<PageData> userList = userService.listPdPageUser(page); // 列出用户列表

		// 2015-7-24 18:04:00任毅增加：需根据（管理/普通）用户区分可控用户范围--------------------start
		boolean ifAdmin = false;
		List<String> glRoleIds = new ArrayList<String>();// 所有管理角色的RoleId集合
		glRoleIds.add("1");
		// 查询所有gl角色（角色部门id=1下面的角色）
		PageData roleConditionPd = new PageData();
		roleConditionPd.put("ROLE_ID", "1");
		List<Role> glRoleIdPds = roleService.listAllRolesByPId(roleConditionPd);
		for (Role role : glRoleIdPds) {
			glRoleIds.add(role.getROLE_ID());
		}
		// 根据当前用户的角色
		User u = (User) session.getAttribute(Const.SESSION_USER);
		Role role = u.getRole();
		if (glRoleIds.contains(role.getROLE_ID())) {// 系统管理员或系统管理员下的角色
			ifAdmin = true;
		} else {// 普通权限查询用户时需要筛选掉管理用户
			Iterator<PageData> iterator = userList.iterator();
			while (iterator.hasNext()) {
				PageData userPd = iterator.next();
				if (glRoleIds.contains(userPd.getString("ROLE_ID"))) {
					iterator.remove();
				}
			}
		}
		// 2015-7-24 18:04:00任毅增加：需根据（管理/普通）用户区分可控用户范围--------------------end

		roleList = roleService.listAllERRoles();// 列出所有二级角色
		if (!ifAdmin) {// 如果不是管理员筛选掉管理员角色
			Iterator<Role> roleIterator =roleList.iterator();
			while (roleIterator.hasNext()) {
				Role r =roleIterator.next();
				if (glRoleIds.contains(r.getROLE_ID())) {
					roleIterator.remove();
				}
			}
		}

		List<Organ> organs = organService.listTreeOrgan("0");

		/* 调用权限 */
		this.getQx(); // ================================================================================
		/* 调用权限 */

		mv.setViewName("system/user/user_list");
		mv.addObject("userList", userList);
		mv.addObject("roleList", roleList);
		mv.addObject("organList", organs);
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 删除用户
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			userService.deleteU(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	//===================================================================================================
	
	
	
	
//	/**
//	 * 保存
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping(value="/add",method=RequestMethod.POST)
//	public ModelAndView add(){
//		mv.clear();
//		pd.clear();
//		try{
//			pd = this.getPageData();
//			pd.put("password", MD5.md5(pd.get("loginname").toString()+pd.get("password").toString()));
//			if(pd.get("roleId").toString().equals("2")){
//				//String upid = UuidUtil.get32UUID();
//				String upid = pd.getString("supplier_id");
//				pd.put("supplier_id", upid);
//			}
//			userService.add(pd);
//			mv.addObject("msg","success");
//			mv.setViewName("save_result");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//			mv.addObject("msg","failed");
//		}
//		
//		return mv;
//	}
//	
//	/**
//	 * 保存用户信息
//	 * @param user
//	 * @return
//	 */
//	@RequestMapping(value="/toEdit",method=RequestMethod.POST)
//	public ModelAndView toEdit(User user){
//		
//		try{
//			if(user.getUSER_ID()==null || "".equals(user.getUSER_ID())){
//				if(user.getPASSWORD() != null || !user.getPASSWORD().equals(""))
//					user.setPASSWORD(MD5.md5(user.getUSERNAME()+user.getPASSWORD()));
//				if(userService.insertUser(user)==false){
//					mv.addObject("msg","failed");
//				}else{
//					mv.addObject("msg","success");
//				}
//			}else{
//				user.setPASSWORD(MD5.md5(user.getUSERNAME()+user.getPASSWORD()));
//				userService.updateUserBaseInfo(user);
//			}
//			mv.setViewName("save_result");
//		} catch(Exception e){
//			e.printStackTrace();
//		}
//		return mv;
//	}
	
	
	
	
	
/*	*//**
	 * 导出用户信息到excel
	 * @return
	 *//*
	@RequestMapping(value="/excel")
	public ModelAndView export2Excel(){
		ModelAndView mv = new ModelAndView();
		
		try{
			Map<String,Object> dataMap = new HashMap<String,Object>();
			List<String> titles = new ArrayList<String>();
			titles.add("用户名");
			titles.add("名称");
			titles.add("角色");
			titles.add("最近登录");
			dataMap.put("titles", titles);
			List<User> userList = userService.listAllUser();
			dataMap.put("userList", userList);
			UserExcelView erv = new UserExcelView();
			mv = new ModelAndView(erv,dataMap);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}*/
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	

	/*
	 * 菜单 =====================================================================================================
	 */
	public void getHome(HttpSession session, Page page) throws Exception{
			pd = this.getPageData();
			String roleRights = session.getAttribute("sessionRoleRights").toString();
			//String userRights = session.getAttribute("sessionUserRights").toString();
			String userRights = "";
			//避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
			List<Menu> menuList = menuService.listAllMenu();
			if(Tools.notEmpty(userRights) || Tools.notEmpty(roleRights)){
				for(Menu menu : menuList){
					menu.setHasMenu(RightsHelper.testRights(userRights, menu.getMENU_ID()) || RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if(menu.isHasMenu()){
						List<Menu> subMenuList = menu.getSubMenu();
						for(Menu sub : subMenuList){
							sub.setHasMenu(RightsHelper.testRights(userRights, sub.getMENU_ID()) || RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
				mv.addObject("menuList", menuList);
	}
	
	//===========================================================================================================
}
