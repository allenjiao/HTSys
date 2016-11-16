package com.fh.controller.system.appuser;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.fh.controller.system.role.RoleService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.util.MD5;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;

/** 
 * 类名称：AppuserController
 * 创建人：fuhang 
 * 创建时间：2014年6月28日
 * @version
 */
@Controller
@RequestMapping(value="/appuser")
public class AppuserController extends BaseController {
	
	@Resource(name="appuserService")
	private AppuserService appuserService;
	@Resource(name="roleService")
	private RoleService roleService;
	
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
		//pd.put("STATUS", "0");				//状态
		
		pd.put("PASSWORD", MD5.md5(pd.getString("PASSWORD")));
		
		if(appuserService.findByUId(pd) != null){
			mv.addObject("msg","failed");
		}else{
			appuserService.saveU(pd);
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
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
		
		appuserService.editU(pd);
		mv.addObject("msg","success");
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
			if(appuserService.findByUId(pd) != null){
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
	 * 去修改用户页面
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU(){
		mv.clear();
		pd = this.getPageData();
		try {
			
			List<Role> roleList = roleService.listAllappERRoles();			//列出所有二级角色
			
			pd = appuserService.findByUiId(pd);								//根据ID读取
			
			
			mv.setViewName("system/appuser/appuser_edit");
			mv.addObject("msg", "editU");
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 去新增用户页面
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU(){
		mv.clear();
		pd = this.getPageData();
		try {
			List<Role> roleList;
			
			roleList = roleService.listAllappERRoles();			//列出所有二级角色
			
			mv.setViewName("system/appuser/appuser_edit");
			mv.addObject("msg", "saveU");
			mv.addObject("pd", pd);
			mv.addObject("roleList", roleList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}						
		return mv;
	}
	
	/**
	 * 显示用户列表(APP用户组)
	 */
	@RequestMapping(value="/listUsers")
	public ModelAndView listUsers(HttpSession session, Page page){
		mv.clear();
		try{
			pd = this.getPageData();
			this.setPdmenu(pd);
			this.getHome(session, page);
			
			String USERNAME = pd.getString("USERNAME");
			
			if(null != USERNAME && !"".equals(USERNAME)){
				USERNAME = USERNAME.trim();
				pd.put("USERNAME", USERNAME);
			}
			
			page.setPd(pd);
			List<PageData>	userList = appuserService.listPdPageUser(page);			//列出用户列表
			List<Role> roleList = roleService.listAllappERRoles();					//列出所有会员二级角色
			
			/*调用权限*/
			this.getQx(); //================================================================================
			/*调用权限*/
			
			mv.setViewName("system/appuser/appuser_list");
			mv.addObject("userList", userList);
			mv.addObject("roleList", roleList);
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
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
			appuserService.deleteU(pd);
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	//===================================================================================================
	
	
	
	
	
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/toEdit",method=RequestMethod.POST)
	public ModelAndView toEdit(User user){
		mv.clear();
		try{
			if(user.getUSER_ID()==null || "".equals(user.getUSER_ID())){
				if(user.getPASSWORD() != null || !user.getPASSWORD().equals(""))
					user.setPASSWORD(MD5.md5(user.getUSERNAME()+user.getPASSWORD()));
				if(appuserService.insertUser(user)==false){
					mv.addObject("msg","failed");
				}else{
					mv.addObject("msg","success");
				}
			}else{
				user.setPASSWORD(MD5.md5(user.getUSERNAME()+user.getPASSWORD()));
				appuserService.updateUserBaseInfo(user);
			}
			mv.setViewName("save_result");
		} catch(Exception e){
			e.printStackTrace();
		}
		return mv;
	}
	
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	@Resource(name="menuService")
	private MenuService menuService;
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
