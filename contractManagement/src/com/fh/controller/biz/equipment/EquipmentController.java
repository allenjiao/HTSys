package com.fh.controller.biz.equipment;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.biz.equipment.vo.EquipmentGatherConfigVo;
import com.fh.controller.system.menu.MenuService;
import com.fh.controller.system.organ.OrganService;
import com.fh.controller.system.role.RoleService;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Organ;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.ObjectExcelRead;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;
import com.gd.utils.JsonUtil;
import com.gd.websocket.WebSocketHelper;
import com.gd.websocket.vo.equipmentdelete.EquipmentDelete;
import com.gd.websocket.vo.equipmentdelete.EquipmentDeleteVo;

/**
 * 设备管理处理器
 * @author tyutNo4<br>
 * 创建时间：2015-1-30下午8:40:17
 */
@Controller
@RequestMapping(value="/equipment")
public class EquipmentController extends BaseController {
	private static final Logger log = Logger.getLogger(EquipmentController.class);
	
//	@Resource(name="thirdPartySystemService")
//	private ThirdPartySystemService thirdPartySystemService;
//	@Resource(name="softwareService")
//	private SoftwareService softwareService;
	@Resource(name="equipmentService")
	private EquipmentService equipmentService;
//	@Resource(name="equipmentTypeService")
//	private EquipmentTypeService equipmentTypeService;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="menuService")
	private MenuService menuService;
	@Resource(name="organService")
	private OrganService organService;
	
	//===================================================================================================
	
	
	/* ===============================权限================================== */
	@SuppressWarnings("unchecked")
	public void getQx(){
		HttpSession session = this.getRequest().getSession();
		Map<String, Integer> map = (Map<String, Integer>)session.getAttribute("QX");
		mv.addObject("QX",map);
	}
	/* ===============================权限================================== */
	
	@RequestMapping(value="/editU")
	public ModelAndView editU(PrintWriter out) throws Exception{
		mv.clear();
		pd = this.getPageData();

		//设备更新 update（不更新BINDING_ID、POSITION_ID）
		
//		String EQUIPMENT_TYPE_ID = pd.getString("EQUIPMENT_TYPE_ID");
//		pd.put("BIZ_SYSTEM_NAME", "");// 所属业务系统名称
//		// EQUIPMENT_TYPE_NAME自动冗余
//		if (StringUtils.isNotBlank(EQUIPMENT_TYPE_ID)) {
//			PageData pd1 = new PageData();
//			pd1.put("EQUIPMENT_TYPE_ID", EQUIPMENT_TYPE_ID);
//			pd1 = equipmentTypeService.findByEquipmentTypeId(pd1);
//			pd.put("EQUIPMENT_TYPE_NAME", pd1.getString("EQUIPMENT_TYPE_NAME"));
//		} else {
//			pd.put("EQUIPMENT_TYPE_NAME", "");
//		}
		
		equipmentService.editEquipment(pd);
		mv.addObject("msg","XXX");
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/editU1")
	public ModelAndView editU1(PrintWriter out) throws Exception{
		mv.clear();
		pd = this.getPageData();

		String EQUIPMENT_ID = pd.getString("EQUIPMENT_ID");
		//更新设备扩展属性表
		//1、取页面数据
		@SuppressWarnings("unchecked")
		Set<String> sets = pd.keySet();
		Iterator<String> its = sets.iterator();
		while (its.hasNext()) {
			String key = its.next();
			if (key.indexOf("VALUE") >= 0) {
				String name = key.substring(5);
				String value = pd.getString(key);
				
				//2、判断数据是该update or insert
				PageData pd1 = new PageData();
				pd1.put("EQUIPMENT_ID", EQUIPMENT_ID);
				pd1.put("ATTR_NAME", name);
				List<PageData> pds = equipmentService.listEquipmentExtAttr(pd1);
				
				PageData pdUpdate = new PageData();
				if (pds != null && pds.size() > 0) {//update
					pdUpdate.put("ATTR_ID", pds.get(0).getString("ATTR_ID"));
					pdUpdate.put("EQUIPMENT_ID", EQUIPMENT_ID);
					pdUpdate.put("ATTR_NAME", name);
					pdUpdate.put("VALUE", value);
					equipmentService.editEquipmentExtAttr(pdUpdate);
				} else {//insert save
					pdUpdate.put("ATTR_ID", UuidUtil.get32UUID());
					pdUpdate.put("EQUIPMENT_ID", EQUIPMENT_ID);
					pdUpdate.put("ATTR_NAME", name);
					pdUpdate.put("VALUE", value);
					equipmentService.saveEquipmentExtAttr(pdUpdate);
				}
			}
		}
		
		//3、do it
		
//		mv.addObject("msg", "success");
		mv.addObject("msg", "xxx");// 不刷新local界面则msg设随意值
		mv.setViewName("save_result");
		return mv;
	}
	
	@RequestMapping(value="/editU2")
	public ModelAndView editU2(PrintWriter out) throws Exception{
		mv.clear();
		pd = this.getPageData();

		String EQUIPMENT_ID = pd.getString("EQUIPMENT_ID");
//		String POSITION_ID = pd.getString("POSITION_ID");
		
		//1、判断该设备对应的位置是否存在
		PageData position = equipmentService.findByPositionId(pd);
		//2、如果存在则update，
		if (position != null) {
			equipmentService.editEquipmentPosition(pd);
			//1、删广播 
			//删除位置，并作广播
			String equipmentId = EQUIPMENT_ID;
			
			EquipmentDelete vo1 = new EquipmentDelete();
			vo1.setEquipmentId(equipmentId);
			
			EquipmentDeleteVo vos1 = new EquipmentDeleteVo();
			vos1.setType("equipment_delete");
			vos1.getEquipmentDeletes().add(vo1);
			
			String result1 = JsonUtil.toJson(vos1);
			logger.info("result = [ " + result1 + " ]");
			
			WebSocketHelper.broadcast(result1);
			//2、加广播
			PageData equipment = equipmentService.findByEquipmentId(pd);
			List<PageData> equipments = new ArrayList<PageData>();
			equipments.add(equipment);
//			String result = toJsonAdd(equipments);
//			logger.info("result = [ " + result + " ]");
//			
//			WebSocketHelper.broadcast(result);
			
		} else {//如果不存在则insert，并做广播通知
			equipmentService.saveEquipmentPosition(pd);
			PageData equipment = equipmentService.findByEquipmentId(pd);
			List<PageData> equipments = new ArrayList<PageData>();
			equipments.add(equipment);
//			String result = toJsonAdd(equipments);
//			logger.info("result = [ " + result + " ]");
			
//			WebSocketHelper.broadcast(result);
		}
		
//		mv.addObject("msg", "success");
		mv.addObject("msg", "xxx");// 不刷新local界面则msg设随意值
		mv.setViewName("save_result");
		return mv;
	}
	
//	private String toJsonAdd(List<PageData> cabinetsOri) {
//		EquipmentAddVo vos = new EquipmentAddVo();
//		vos.setType("equipment_add");
//		
//		PageData equipmentTypesPd = new PageData();// 所有设备类型id-obj
//		try {
//			List<PageData> eTs = equipmentTypeService.listEquipmentType(new PageData());
//			for (PageData eT : eTs) {
//				equipmentTypesPd.put(eT.get("EQUIPMENT_TYPE_ID"), eT);
//			}
//		} catch (Exception e) {
//			logger.error(e);
//		}
//		
//		for(PageData c : cabinetsOri) {
//			String equipmentId = c.getString("EQUIPMENT_ID");
////			String positionId = c.getString("POSITION_ID");
//			String rootEquipmentType = c.getString("EQUIPMENT_TYPE_ID");// 假设自己就是根类型id
//			for (PageData rootETPd = (PageData) equipmentTypesPd.get(rootEquipmentType); rootETPd != null
//					&& !StringUtils.equalsIgnoreCase(rootETPd.getString("PARENT_EQUIPMENT_TYPE_ID"), "#");) {
//				rootETPd = (PageData) equipmentTypesPd.get(rootETPd.getString("PARENT_EQUIPMENT_TYPE_ID"));
//				rootEquipmentType = rootETPd.getString("EQUIPMENT_TYPE_ID");
//			}
////			String parentEquipmentType =c.getString("PARENT_EQUIPMENT_TYPE_ID");
//			//String EQUIPMENT_TYPE_NAME = c.getString("EQUIPMENT_TYPE_NAME");
//			/*if (EQUIPMENT_TYPE_NAME.indexOf("安全设备") >= 0) {
//				equipmentType = "1";
//			} else if (EQUIPMENT_TYPE_NAME.indexOf("存储设备") >= 0) {
//				equipmentType = "2";
//			} else if (EQUIPMENT_TYPE_NAME.indexOf("网络设备") >= 0) {
//				equipmentType = "3";
//			} else if (EQUIPMENT_TYPE_NAME.indexOf("服务器") >= 0) {
//				equipmentType = "4";
//			} else if (EQUIPMENT_TYPE_NAME.indexOf("数据库") >= 0) {
//				equipmentType = "5";
//			} else if (EQUIPMENT_TYPE_NAME.indexOf("其他") >= 0) {
//				equipmentType = "6";
//			} else {
//				equipmentType = "7";
//			}*/
//			
//			String x2d = c.getString("X_2D");
//			String y2d = c.getString("Y_2D");
//			String y2dSize = c.getString("Y_2D_SIZE");
//			String x2dSize = c.getString("X_2D_SIZE");
//			String shortName2d = c.getString("SHORT_NAME_2D");
//			String rowId = c.getString("ROW_NO");
//
//			EquipmentAdd vo = new EquipmentAdd();
//			vo.setEquipmentId(equipmentId);
//			vo.setEquipmentType(rootEquipmentType);
//			vo.setX2d(x2d);
//			vo.setY2d(y2d);
//			vo.setX2dSize(x2dSize);
//			vo.setY2dSize(y2dSize);
//			vo.setShortName2d(shortName2d);
//			vo.setRowId(rowId);
//			
//			vos.getEquipmentAdds().add(vo);
//			
//		}
//		String result = JsonUtil.toJson(vos);
//		return result;
//	}
	
	/**
	 * 修改采集配置
	 */
	@RequestMapping(value = "/editU3")
	public ModelAndView editU3(EquipmentGatherConfigVo configVo) throws Exception {
		mv.clear();
		pd = this.getPageData();
		String EQUIPMENT_ID = pd.getString("EQUIPMENT_ID");

		List<EquipmentGatherConfigVo> configVos = configVo.getVos();
		List<PageData> pds = new ArrayList<PageData>();// 批量插入
		for (EquipmentGatherConfigVo equipmentGatherConfigVo : configVos) {
			if (equipmentGatherConfigVo != null && StringUtils.isNotBlank(equipmentGatherConfigVo.getATTR_NAME())) {
				PageData pdThis = new PageData();
				pdThis.put("CONFIG_ID", equipmentGatherConfigVo.getCONFIG_ID());
				pdThis.put("EQUIPMENT_ID", EQUIPMENT_ID);
				pdThis.put("EQUIPMENT_NAME", "");
				pdThis.put("ATTR_NAME", equipmentGatherConfigVo.getATTR_NAME());
				pdThis.put("GATHER_SYSTEM_ID", "");
				pdThis.put("GATHER_ATTR_CODE", equipmentGatherConfigVo.getGATHER_ATTR_CODE());
				pdThis.put("ATTR_UNIT", equipmentGatherConfigVo.getATTR_UNIT());
				pdThis.put("CHANNEL_NO", equipmentGatherConfigVo.getCHANNEL_NO());
				pdThis.put("X_3D", "");
				pdThis.put("Y_3D", "");
				pdThis.put("Z_3D", "");
				pdThis.put("ATTR_VALUE", "0");
				pdThis.put("ATTR_CODE", equipmentGatherConfigVo.getATTR_CODE());
				pdThis.put("GATHER_TYPE", equipmentGatherConfigVo.getGATHER_TYPE());
				pdThis.put("RELATED_CONFIG_ID", equipmentGatherConfigVo.getRELATED_CONFIG_ID() == null ? ""
						: equipmentGatherConfigVo.getRELATED_CONFIG_ID());
				pds.add(pdThis);
			}
		}
		equipmentService.saveBatchEquipmentGatherConfig(EQUIPMENT_ID, pds);

		// 3、do it

//		mv.addObject("msg", "success");
		mv.addObject("msg", "xxx");// 不刷新local界面则msg设随意值
		mv.setViewName("save_result");
		return mv;
	}

	/**
	 * 新增设备：查询最大设备表id，手动自增，设备表与位置表id相同，jp需求-by ry
	 * 
	 * @param out
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addU")
	public ModelAndView addU(PrintWriter out) throws Exception {
		mv.clear();
		// 新增id
//		int id = 1;
//		PageData pdMaxId = equipmentService.findMaxId(new PageData());
//		Object maxObj = pdMaxId.get("MID");
//		if (maxObj instanceof String) {
//			id = Integer.parseInt(maxObj+"") + 1;
//		}else if (maxObj instanceof BigDecimal) {
//			id = ((BigDecimal)maxObj).intValue()+1;
//		}else if (maxObj instanceof Integer) {
//			id = (Integer)maxObj +1;
//		}
//
//		pd = this.getPageData();
//		pd.put("EQUIPMENT_ID", id);
//		pd.put("BINDING_ID", "");
////		pd.put("POSITION_ID", "");
//
//		// 补齐设备表新增字段，需要时再更新页面
////		pd.put("ASSET_NO", "");// 资产编号
//		pd.put("BIZ_SYSTEM_NAME", "");// 所属业务系统名称
//		pd.put("EQUIPMENT_TYPE_DETAIL_ID", "");// 详细设备类型信息ID
//		pd.put("EQUIPMENT_TYPE_DETAIL_NAME", "");// 详细设备类型信息
////		pd.put("EQUIPMENT_MODEL", "");// 设备型号
////		pd.put("DEPARTMENT", "");// 所属部门
////		pd.put("ALARM_STATUS", "1");// 报警状态，标识该设备现在是否报警中0报警中1未报警
////		pd.put("PUTAWAY_TIME", "");// 上架时间
//		//
//		// 冗余设备类型名称
//		String EQUIPMENT_TYPE_ID = pd.getString("EQUIPMENT_TYPE_ID");
//		if (StringUtils.isNotBlank(EQUIPMENT_TYPE_ID)) {
//			PageData pd1 = new PageData();
//			pd1.put("EQUIPMENT_TYPE_ID", EQUIPMENT_TYPE_ID);
//			pd1 = equipmentTypeService.findByEquipmentTypeId(pd1);
//			pd.put("EQUIPMENT_TYPE_NAME", pd1.getString("EQUIPMENT_TYPE_NAME"));
//		} else {
//			pd.put("EQUIPMENT_TYPE_NAME", "");
//		}
//		// 为根设备则PARENT_EQUIPMENT_ID设为#
//		String PARENT_EQUIPMENT_ID = pd.getString("PARENT_EQUIPMENT_ID");
//		if (StringUtils.isBlank(PARENT_EQUIPMENT_ID)) {
//			pd.put("PARENT_EQUIPMENT_ID", "#");
//		}
		
		equipmentService.saveEquipment(pd);
		mv.addObject("msg", "XXX");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去修改用户页面
	 */
	@RequestMapping(value="/goEditU")
	public ModelAndView goEditU() throws Exception{
		
		logger.info("编辑tyut");
		
		mv.clear();
		pd = this.getPageData();
//		
//		//设备基本属性
//		String EQUIPMENT_ID = pd.getString("EQUIPMENT_ID");
//		pd.put("EQUIPMENT_ID", EQUIPMENT_ID);
//		pd = equipmentService.findByEquipmentId(pd);
//		
//		// 运行软件信息
//		PageData pdsoft = new PageData();
//		List<PageData> softwares = softwareService.listSoftware(pdsoft);
//		
//		//父设备信息
//		PageData pd2 = new PageData();
//		List<PageData> equipments = equipmentService.listEquipment(pd2);
//		
//		//设备类型
//		PageData pd1 = new PageData();
//		List<PageData> equipmentTypes = equipmentTypeService.listEquipmentType(pd1);
//		
//		//所属系统
//		PageData pdsystem = new PageData();
//		List<PageData> systems = thirdPartySystemService.listThirdPartySystem(pdsystem);
//		
//		List<Organ> organs = organService.listTreeOrgan("0");
//		
//		mv.addObject("equipmentTypes", equipmentTypes);
//		mv.addObject("equipments", equipments);
//		mv.addObject("softwares", softwares);
//		mv.addObject("systems", systems);
//		mv.addObject("organs", organs);
		
		mv.setViewName("biz/equipment/equipment_ed");
		//mv.addObject("equipmentType", equipmentType);
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 附加属性
	 */
	@RequestMapping(value="/goEditU1")
	public ModelAndView goEditU1() throws Exception{
		
		logger.info("编辑tyut1");
		
		mv.clear();
		pd = this.getPageData();
		
//		String EQUIPMENT_ID = pd.getString("EQUIPMENT_ID");
//		String EQUIPMENT_TYPE_ID = pd.getString("EQUIPMENT_TYPE_ID"); 
//		pd.put("EQUIPMENT_TYPE_ID", EQUIPMENT_TYPE_ID);
//		//1、通过EQUIPMENT_TYPE_ID去模板表中获取属性列表
//		List<PageData> attrNames = equipmentTypeService.listEquipmentTypeAttr(pd);
//		//2、循环属性列表去属性扩展表中取值，有 or 没有
//		List<PageData> attrs = new ArrayList<PageData>();
//		if (attrNames != null) {
//			for (PageData p : attrNames) {
//				PageData pdNew = new PageData();
//				
//				PageData pd1 = new PageData();
//				pd1.put("EQUIPMENT_ID", EQUIPMENT_ID);
//				pd1.put("ATTR_NAME", p.getString("NAME"));
//				List<PageData> pds = equipmentService.listEquipmentExtAttr(pd1);
//				if (pds != null && pds.size() > 0) {
//					pdNew.put("ATTR_NAME", p.getString("NAME"));
//					pdNew.put("VALUE", pds.get(0).getString("VALUE"));
//				} else {//置为空
//					pdNew.put("ATTR_NAME", p.getString("NAME"));
//					pdNew.put("VALUE", "");
//				}
//				
//				attrs.add(pdNew);
//			}
//		}
//		
//		//3、将属性名称、属性值进行NAME+CODE /VALUE+CODE处理，显示
//		
//		pd.put("EQUIPMENT_ID", EQUIPMENT_ID);
		
		mv.setViewName("biz/equipment/equipment_ed1");
//		mv.addObject("attrs", attrs);
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 位置
	 */
	@RequestMapping(value="/goEditU2")
	public ModelAndView goEditU2() throws Exception{
		
		logger.info("编辑tyut2");
		
		mv.clear();
		pd = this.getPageData();
		
		String EQUIPMENT_ID = pd.getString("EQUIPMENT_ID");
		
		pd.put("POSITION_ID", EQUIPMENT_ID);
		
		pd = equipmentService.findByPositionId(pd);
		if (pd == null) {// 没有位置信息则恢复
			pd = this.getPageData();
		}
		// 用同id by ry
		pd.put("POSITION_ID", EQUIPMENT_ID);
		pd.put("EQUIPMENT_ID", EQUIPMENT_ID);
		
		mv.setViewName("biz/equipment/equipment_ed2");
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	private Map<String, String> eqIdMap = new HashMap<String, String>();
	/**
	 * 采集配置
	 */
	@RequestMapping(value="/goEditU3")
	public ModelAndView goEditU3() throws Exception{
		
		logger.info("编辑tyut3");
		
		if (eqIdMap == null || eqIdMap.isEmpty()) {
			List<PageData> eqPd = equipmentService.listEquipment(new PageData());
			for (PageData eq : eqPd) {
				eqIdMap.put(eq.getString("EQUIPMENT_ID"), eq.getString("EQUIPMENT_NAME"));
			}
		}
		
		mv.clear();
		pd = this.getPageData();
		
		List<PageData> configs = equipmentService.listEquipmentGatherConfig(pd);
		List<PageData> otherConfigs = equipmentService.listEquipmentGatherConfig(new PageData());
		Iterator<PageData> removeIterator = otherConfigs.iterator();
		while (removeIterator.hasNext()) {// 去掉业务不需要的的
			PageData otherConfig = removeIterator.next();
			if (StringUtils.equals(otherConfig.getString("EQUIPMENT_ID"), pd.getString("EQUIPMENT_ID"))) {// 自己的删掉
				removeIterator.remove();
			}else if (!StringUtils.equals(otherConfig.getString("GATHER_TYPE"), "0")) {// 关联采集的删掉
				removeIterator.remove();
			} else {// 没被删掉则把设备名称附在后面（）中
				otherConfig.put("ATTR_NAME",
						otherConfig.getString("ATTR_NAME") + "(" + eqIdMap.get(otherConfig.getString("EQUIPMENT_ID"))
								+ ")");
			}
		}
		
		mv.setViewName("biz/equipment/equipment_ed3");
		//mv.addObject("functions", functions);
		mv.addObject("configs", configs);
		mv.addObject("otherConfigs", otherConfigs);
		mv.addObject("otherConfigsStr", JsonUtil.toJson(otherConfigs));
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 打开上传EXCEL页面
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		mv.clear();
		mv.setViewName("biz/equipment/uploadexcel");
		return mv;
	}
	
	/**
	 * 下载模版
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATH + "equipment.xlsx", "18层机房设备统计表模板.xls");
		
	}
	
	/**
	 * 从EXCEL导入到数据库
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(@RequestParam(value="excel",required=false) MultipartFile file) throws Exception{
		mv.clear();
		
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATH;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");						//执行上传
			
			//List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);	//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 17, 1, 2);	//执行读EXCEL操作,读出的数据导入List 2:从第18行开始；0:从第B列开始；0:第3个sheet
			/*存入数据库操作======================================*/
			
			/*存入数据库操作======================================*/
			
			mv.addObject("msg","success");
		}
		
		mv.setViewName("save_result");
		return mv;
	}
	
	/**
	 * 去新增用户页面
	 */
	@RequestMapping(value="/goAddU")
	public ModelAndView goAddU()throws Exception{
		mv.clear();
		pd = this.getPageData();
//		
//		//父设备信息
//		PageData pd2 = new PageData();
//		List<PageData> equipments = equipmentService.listEquipment(pd2);
//		
//		// 运行软件信息
//		PageData pdsoft = new PageData();
//		List<PageData> softwares = softwareService.listSoftware(pdsoft);
//				
//		//设备类型
//		PageData pd1 = new PageData();
//		List<PageData> equipmentTypes = equipmentTypeService.listEquipmentType(pd1);
//		
//		//所属系统
//		PageData pdsystem = new PageData();
//		List<PageData> systems = thirdPartySystemService.listThirdPartySystem(pdsystem);
//		
//		List<Organ> organs = organService.listTreeOrgan("0");
//		
//		mv.addObject("equipmentTypes", equipmentTypes);
//		mv.addObject("equipments", equipments);
//		mv.addObject("softwares", softwares);
//		mv.addObject("systems", systems);
//		mv.addObject("organs", organs);
		
		mv.setViewName("biz/equipment/equipment_add");
		mv.addObject("pd", pd);

		return mv;
	}
	
	/**
	 * 显示设备列表
	 * @param session
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/listEquipments")
	public ModelAndView listEquipments(HttpSession session, Page page)throws Exception{
		mv.clear();
		this.getHome(session, page);
		pd = this.getPageData();
		this.setPdmenu(pd);
		
		page.setPd(pd);
//		List<PageData> equipmentList = equipmentService.listPdPageEquipment(page);
		
		List<Organ> organs = organService.listTreeOrgan("0");
		
		/*调用权限*/
		this.getQx(); //================================================================================
		/*调用权限*/
		
//		PageData pd1 = new PageData();
//		List<PageData> equipmentTypes = equipmentTypeService.listEquipmentType(pd1);
		mv.addObject("equipmentTypes", new ArrayList<PageData>());
		
		mv.setViewName("biz/equipment/equipment_list");
		mv.addObject("equipmentList", new ArrayList<PageData>());
		mv.addObject("pd", pd);
		mv.addObject("organs", organs);
		
		return mv;
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping(value="/deleteU")
	public void deleteU(PrintWriter out){
		logger.info("删除tyut");
		mv.clear();
		try{
			pd = this.getPageData();
			
			String EQUIPMENT_ID = pd.getString("EQUIPMENT_ID");
//			String POSITION_ID = pd.getString("POSITION_ID");
			
			//删除设备
			pd.put("EQUIPMENT_ID", EQUIPMENT_ID);
			equipmentService.deleteEquipment(pd);
			//删除扩展属性？TODO再说
		
			//删除位置
			pd.put("POSITION_ID", EQUIPMENT_ID);
			equipmentService.deletePosition(pd);
			
			//并作广播
			String equipmentId = EQUIPMENT_ID;
			
			EquipmentDelete vo = new EquipmentDelete();
			vo.setEquipmentId(equipmentId);
			
			EquipmentDeleteVo vos = new EquipmentDeleteVo();
			vos.setType("equipment_delete");
			vos.getEquipmentDeletes().add(vo);
			
			String result = JsonUtil.toJson(vos);
			logger.info("result = [ " + result + " ]");
			
			WebSocketHelper.broadcast(result);
			
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
//			equipmentService.add(pd);
//			mv.addObject("msg","success");
//			mv.setViewName("save_result");
//		} catch(Exception e){
//			logger.error(e.toString(), e);
//			mv.addObject("msg","failed");
//		}
//		
//		return mv;
//	}
	
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
//				if(equipmentService.insertUser(user)==false){
//					mv.addObject("msg","failed");
//				}else{
//					mv.addObject("msg","success");
//				}
//			}else{
//				user.setPASSWORD(MD5.md5(user.getUSERNAME()+user.getPASSWORD()));
//				equipmentService.updateUserBaseInfo(user);
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
