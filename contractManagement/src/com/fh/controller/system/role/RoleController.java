package com.fh.controller.system.role;

import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.menu.MenuService;
import com.fh.controller.system.organ.OrganService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Organ;
import com.fh.entity.system.Role;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
/** 
 * 类名称：RoleController
 * 创建人：fuhang 
 * 创建时间：2014年6月30日
 * @version
 */
@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
	
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="organService")
	private OrganService organService;
	
	/**
	 * 权限(增删改查)
	 */
	@RequestMapping(value="/qx")
	public ModelAndView qx()throws Exception{
		mv.clear();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			roleService.updateQx(msg,pd);
			
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	/**
	 * 客服权限
	 */
	@RequestMapping(value="/kfqx")
	public ModelAndView kfqx()throws Exception{
		mv.clear();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			roleService.updateKFQx(msg,pd);
			
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	/**
	 * c权限
	 */
	@RequestMapping(value="/gysqxc")
	public ModelAndView gysqxc()throws Exception{
		mv.clear();
		try{
			pd = this.getPageData();
			String msg = pd.getString("msg");
			roleService.gysqxc(msg,pd);
			
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 列表
	 */
	@RequestMapping
	public ModelAndView list(HttpSession session, Page page) throws Exception {
		mv.clear();
		this.getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);

		String roleId = pd.getString("ROLE_ID");
		if (roleId == null || "".equals(roleId)) {
			pd.put("ROLE_ID", "1");
		}
		List<Role> roleList = roleService.listAllRoles(); // 列出所有部门
		List<Role> roleList_z = roleService.listAllRolesByPId(pd); // 列出此部门的所有下级

		List<PageData> kefuqxlist = roleService.listAllkefu(pd); // 管理权限列表
		List<PageData> gysqxlist = roleService.listAllGysQX(pd); // 用户权限列表

		List<Organ> organs = organService.listTreeOrgan("0");
		JSONArray array = JSONArray.fromObject(organs);
		String json = array.toString();
		json = json.replaceAll("ORGAN_ID", "id").replaceAll("ORGAN_NAME", "name").replaceAll("PARENT_ID", "pId");

//		String json = "[{\"id\":\"1\",\"name\":\"系统管理\",\"MENU_ORDER\":\"1\",\"MENU_URL\":\"\",\"PARENT_ID\":\"0\",\"checked\":true,\"parentMenu\":null,\"nodes\":[{\"id\":\"2\",\"name\":\"组织管理\",\"MENU_ORDER\":\"2\",\"MENU_URL\":\"role.do?1=1\",\"PARENT_ID\":\"1\",\"checked\":true,\"parentMenu\":null,\"nodes\":[],\"target\":\"\"},{\"id\":\"5\",\"name\":\"系统用户\",\"MENU_ORDER\":\"3\",\"MENU_URL\":\"user/listUsers.do?1=1\",\"PARENT_ID\":\"1\",\"checked\":true,\"parentMenu\":null,\"nodes\":[],\"target\":\"\"},{\"id\":\"4\",\"name\":\"会员管理\",\"MENU_ORDER\":\"4\",\"MENU_URL\":\"appuser/listUsers.do?1=1\",\"PARENT_ID\":\"1\",\"checked\":true,\"parentMenu\":null,\"nodes\":[],\"target\":\"\"}],\"target\":\"\"},{\"id\":\"6\",\"name\":\"业务\",\"MENU_ORDER\":\"2\",\"MENU_URL\":\"\",\"PARENT_ID\":\"0\",\"checked\":true,\"parentMenu\":null,\"nodes\":[{\"id\":\"7\",\"name\":\"订单管理\",\"MENU_ORDER\":\"1\",\"MENU_URL\":\"order.do?1=1\",\"PARENT_ID\":\"6\",\"checked\":true,\"parentMenu\":null,\"nodes\":[],\"target\":\"\"}],\"target\":\"\"}] ";
		System.out.println(json);

		pd = roleService.findObjectById(pd); // 取得点击部门

		mv.addObject("zTreeNodes", json);
		mv.addObject("pd", pd);
		mv.addObject("kefuqxlist", kefuqxlist);
		mv.addObject("gysqxlist", gysqxlist);
		mv.addObject("roleList", roleList);
		mv.addObject("roleList_z", roleList_z);
		mv.setViewName("system/role/role_list");

		return mv;
	}
	
	/**
	 * 新增页面
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(Page page){
		mv.clear();
		try{
			mv.clear();
			pd = this.getPageData();
			
			mv.setViewName("system/role/role_add");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	/**
	 * 保存新增信息
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView add()throws Exception{
		mv.clear();
		try{
			pd = this.getPageData();
			
			String parent_id = pd.getString("PARENT_ID");		//父类角色id
			pd.put("ROLE_ID", parent_id);
			if("0".equals(parent_id)){
				pd.put("RIGHTS", "");
			}else{
				String rights = roleService.findObjectById(pd).getString("RIGHTS");
				pd.put("RIGHTS", (null == rights)?"":rights);
			}

			pd.put("QX_ID", "");
			
			String UUID = this.get32UUID();
			if("4".equals(parent_id)){			//判断是不是客服
				pd.put("GL_ID", UUID);
				pd.put("FX_QX", 0);				//发信权限
				pd.put("FW_QX", 0);				//服务权限
				pd.put("QX1", 0);				//操作权限
				pd.put("QX2", 0);				//产品权限
				pd.put("QX3", 0);				//预留权限
				pd.put("QX4", 0);				//预留权限
				roleService.saveKeFu(pd);										//保存到客服权限表
				pd.put("QX_ID", UUID);
			}
			
			if("6".equals(parent_id)){			//判断是不是其他
				pd.put("U_ID", UUID);
				pd.put("C1", 0);				//每日发信数量
				pd.put("C2", 0);
				pd.put("C3", 0);
				pd.put("C4", 0);
				pd.put("Q1", 0);				//权限1
				pd.put("Q2", 0);				//权限2
				pd.put("Q3", 0);
				pd.put("Q4", 0);
				roleService.saveGYSQX(pd);										//保存到供应商权限表
				pd.put("QX_ID", UUID);
			}
			
			pd.put("ROLE_ID", this.get32UUID());
			pd.put("ADD_QX", "0");
			pd.put("DEL_QX", "0");
			pd.put("EDIT_QX", "0");
			pd.put("CHA_QX", "0");
			roleService.add(pd);
			
			mv.setViewName("save_result");
		} catch(Exception e){
			logger.error(e.toString(), e);
			mv.addObject("msg","failed");
		}
		
		return mv;
	}
	
	/**
	 * 请求编辑用户页面
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID )throws Exception{
		mv.clear();
		try{
			pd = this.getPageData();
			pd.put("ROLE_ID", ROLE_ID);
			pd = roleService.findObjectById(pd);
			
			mv.setViewName("system/role/role_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		return mv;
	}
	
	/**
	 * 请求编辑用户页面
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		mv.clear();
		try{
			pd = this.getPageData();
			pd = roleService.edit(pd);
			
			mv.setViewName("save_result");
			mv.addObject("msg","success");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	/**
	 * 请求角色授权页面
	 */
	@RequestMapping(value="/auth")
	public String auth(@RequestParam String ROLE_ID,Model model)throws Exception{
		
		try{
			List<Menu> menuList = menuService.listAllMenu();
			Role role = roleService.getRoleById(ROLE_ID);
			String roleRights = role.getRIGHTS();
			if(Tools.notEmpty(roleRights)){
				for(Menu menu : menuList){
					menu.setHasMenu(RightsHelper.testRights(roleRights, menu.getMENU_ID()));
					if(menu.isHasMenu()){
						List<Menu> subMenuList = menu.getSubMenu();
						for(Menu sub : subMenuList){
							sub.setHasMenu(RightsHelper.testRights(roleRights, sub.getMENU_ID()));
						}
					}
				}
			}
			JSONArray arr = JSONArray.fromObject(menuList);
			String json = arr.toString();
			//System.out.println(json);
			json = json.replaceAll("MENU_ID", "id").replaceAll("MENU_NAME", "name").replaceAll("subMenu", "nodes").replaceAll("hasMenu", "checked");
			model.addAttribute("zTreeNodes", json);
			model.addAttribute("roleId", ROLE_ID);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return "authorization";
	}
	
	/**
	 * 保存角色权限
	 */
	@RequestMapping(value="/auth/save")
	public void saveAuth(@RequestParam String ROLE_ID,@RequestParam String menuIds,PrintWriter out)throws Exception{
		
		try{
			Role role = roleService.getRoleById(ROLE_ID);
			//添加判断,分别处理菜单权限为空或不为空的情况
			if(StringUtils.isNotBlank(menuIds)){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				role.setRIGHTS(rights.toString());
				roleService.updateRoleRights(role);
				
				pd.put("rights",rights.toString());
			}else{
				role.setRIGHTS("");
				roleService.updateRoleRights(role);
				
				pd.put("rights","");
			}
			
			pd.put("roleId", ROLE_ID);
			roleService.setAllRights(pd);
			
			out.write("success");
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value="/delete")
	public void deleteRole(@RequestParam String ROLE_ID,PrintWriter out)throws Exception{
		try{
			pd.put("ROLE_ID", ROLE_ID);
			List<Role> roleList_z = roleService.listAllRolesByPId(pd);		//列出此部门的所有下级
			if(roleList_z.size() > 0){
				out.write("false");
			}else{
				
				List<PageData> userlist = roleService.listAllUByRid(pd);
				if(userlist.size() > 0){
					out.write("false2");
				}else{
				roleService.deleteRoleById(ROLE_ID);
				out.write("success");
				}
			}
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
