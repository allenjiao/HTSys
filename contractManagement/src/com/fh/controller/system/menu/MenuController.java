package com.fh.controller.system.menu;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.system.Menu;
/** 
 * 类名称：MenuController
 * 创建人：fuhang 
 * 创建时间：2014年7月1日
 * @version
 */
@Controller
@RequestMapping(value="/menu")
public class MenuController extends BaseController {

	@Resource(name="menuService")
	private MenuService menuService;
	
	/**
	 * 显示菜单列表
	 * @param model
	 * @return
	 */
	@RequestMapping
	public ModelAndView list()throws Exception{
		
		try{
			List<Menu> menuList = menuService.listAllParentMenu();
			mv.addObject("menuList", menuList);
			mv.setViewName("system/menu/menu_list");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	/**
	 * 请求新增菜单页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd()throws Exception{
		
		try{
			List<Menu> menuList = menuService.listAllParentMenu();
			mv.addObject("menuList", menuList);
			mv.setViewName("system/menu/menu_add");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	/**
	 * 保存菜单信息
	 * @param menu
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/add")
	public String add(Menu menu,Model model)throws Exception{
		
		try{
			menu.setMENU_ID(String.valueOf(Integer.parseInt(menuService.findMaxId(pd).get("MID").toString())+1));
			menuService.saveMenu(menu);
			model.addAttribute("msg", "ok");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return "save_result";
	}
	
	/**
	 * 请求编辑用户页面
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit(String MENU_ID)throws Exception{
		try{
			pd = this.getPageData();
			pd.put("MENU_ID",MENU_ID);
			pd = menuService.getMenuById(pd);
			List<Menu> menuList = menuService.listAllParentMenu();
			
			mv.addObject("menuList", menuList);
			mv.addObject("pd", pd);
			mv.setViewName("system/menu/menu_edit");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	/**
	 * 请求编辑用户页面
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit()throws Exception{
		try{
			pd = this.getPageData();
			//System.out.println(pd+"===");
			
			String PARENT_ID = pd.getString("PARENT_ID");
			if(null == PARENT_ID || "".equals(PARENT_ID)){
				pd.put("PARENT_ID", "0");
			}
			pd = menuService.edit(pd);
			mv.setViewName("save_result");
			mv.addObject("msg","ok");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	/**
	 * 获取当前菜单的所有子菜单
	 * @param menuId
	 * @param response
	 */
	@RequestMapping(value="/sub")
	public void getSub(@RequestParam String MENU_ID,HttpServletResponse response)throws Exception{
		try {
			List<Menu> subMenu = menuService.listSubMenuByParentId(MENU_ID);
			JSONArray arr = JSONArray.fromObject(subMenu);
			PrintWriter out;
			
			response.setCharacterEncoding("utf-8");
			out = response.getWriter();
			String json = arr.toString();
			out.write(json);
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	
	/**
	 * 删除菜单
	 * @param menuId
	 * @param out
	 */
	@RequestMapping(value="/del")
	public void delete(@RequestParam String MENU_ID,PrintWriter out)throws Exception{
		
		try{
			menuService.deleteMenuById(MENU_ID);
			out.write("success");
			out.flush();
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
}
