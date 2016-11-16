package com.fh.controller.base;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.menu.MenuService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;

/**
 * 标准示例...copy开始新功能模块
 * 
 * @author reny<br>
 *         创建时间：2015-3-10下午2:50:24
 */
//@Controller
@RequestMapping(value = "/test")
public class ExampleController extends BaseController {
	@Resource(name = "menuService")
	private MenuService menuService;

	@RequestMapping(value = "/listTest")
	public ModelAndView listEquipmentTypes(HttpSession session, Page page) throws Exception {
		mv.clear();
		this.getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);

		String EQUIPMENT_TYPE_NAME = pd.getString("EQUIPMENT_TYPE_NAME");
		if (StringUtils.isNotBlank(EQUIPMENT_TYPE_NAME)) {
			pd.put("EQUIPMENT_TYPE_NAME", EQUIPMENT_TYPE_NAME);
		}
		String BRAND_NAME = pd.getString("BRAND_NAME");
		if (StringUtils.isNotBlank(BRAND_NAME)) {
			pd.put("BRAND_NAME", BRAND_NAME);
		}
		String EQUIPMENT_MODEL = pd.getString("EQUIPMENT_MODEL");
		if (StringUtils.isNotBlank(EQUIPMENT_MODEL)) {
			pd.put("EQUIPMENT_MODEL", EQUIPMENT_MODEL);
		}
		page.setPd(pd);
//		List<PageData> equipmentTypeList = equipmentTypeService.listPdPageEquipmentType(page); // 列出用户列表

		/* 调用权限 */
		this.getQx(); // ================================================================================
		/* 调用权限 */

		mv.setViewName("biz/equipment_type/equipmentType_list");
//		mv.addObject("equipmentTypeList", equipmentTypeList);
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 去修改页面
	 */
	@RequestMapping(value = "/goEdit")
	public ModelAndView goEditU() throws Exception {

		mv.clear();
		pd = this.getPageData();

		String EQUIPMENT_TYPE_ID = pd.getString("EQUIPMENT_TYPE_ID");
		pd.put("EQUIPMENT_TYPE_ID", EQUIPMENT_TYPE_ID);
//		pd = equipmentTypeService.findByEquipmentTypeId(pd);

		// pd = equipmentTypeService.findByUiId(pd); //根据ID读取

		mv.setViewName("biz/equipment_type/equipmentType_ed");
		// mv.addObject("equipmentType", equipmentType);
		mv.addObject("pd", pd);

		return mv;
	}

	/**
	 * 修改submit
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView edit(PrintWriter out) throws Exception {
		mv.clear();
		pd = this.getPageData();

		pd.put("BIZ_BRAND_ID", UuidUtil.get32UUID());

//		equipmentTypeService.editEquipmentType(pd);
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 权限
	 */
	@SuppressWarnings("unchecked")
	public void getQx() {
		HttpSession session = this.getRequest().getSession();
		Map<String, Integer> map = (Map<String, Integer>) session.getAttribute("QX");
		mv.addObject("QX", map);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
	}

	/**
	 * 菜单
	 * 
	 * @param session
	 * @param page
	 * @throws Exception
	 */
	public void getHome(HttpSession session, Page page) throws Exception {
		pd = this.getPageData();
		String roleRights = session.getAttribute("sessionRoleRights").toString();
		// String userRights =
		// session.getAttribute("sessionUserRights").toString();
		String userRights = "";
		// 避免每次拦截用户操作时查询数据库，以下将用户所属角色权限、用户权限限都存入session
		List<Menu> menuList = menuService.listAllMenu();
		if (Tools.notEmpty(userRights) || Tools.notEmpty(roleRights)) {
			for (Menu menu : menuList) {
				menu.setHasMenu(RightsHelper.testRights(userRights, menu.getMENU_ID())
						|| RightsHelper.testRights(roleRights, menu.getMENU_ID()));
				if (menu.isHasMenu()) {
					List<Menu> subMenuList = menu.getSubMenu();
					for (Menu sub : subMenuList) {
						sub.setHasMenu(RightsHelper.testRights(userRights, sub.getMENU_ID())
								|| RightsHelper.testRights(roleRights, sub.getMENU_ID()));
					}
				}
			}
		}
		mv.addObject("menuList", menuList);
	}
}
