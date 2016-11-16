package com.fh.controller.system.organ;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.PageData;
/** 
 * 类名称：ZidianController
 * 创建人：fuhang 
 * 创建时间：2014年9月2日
 * @version
 */
@Controller
@RequestMapping(value="/organ")
public class OrganController extends BaseController {
	
	@Resource(name="organService")
	private OrganService organService;
	

	
	/**
	 * 保存
	 */
	@RequestMapping(value="/save")
	public ModelAndView save(PrintWriter out) throws Exception{
		mv.clear();
		pd = this.getPageData();
		PageData pdp = new PageData();
		pdp = this.getPageData();
		
		String PARENT_ID = pd.getString("PARENT_ID");
		pdp.put("ORGAN_ID", PARENT_ID);
		
		if(null == pd.getString("ORGAN_ID") || "".equals(pd.getString("ORGAN_ID"))){
			if(null != PARENT_ID && "0".equals(PARENT_ID)){
				pd.put("ORGAN_LEVEL", 1);
			}else{
				pdp = organService.getOrganById(pdp);
				pd.put("ORGAN_LEVEL", Integer.parseInt(pdp.get("ORGAN_LEVEL").toString()) + 1);
			}
			int newId = 1;
			try {
				newId = Integer.parseInt(organService.findMaxId(new PageData()).toString()) + 1;
			} catch (Exception e) {
			}
			pd.put("ORGAN_ID", newId);	//ID
			organService.saveOrgan(pd);
		}else{
			organService.edit(pd);
		}
		
		
		mv.addObject("msg","ok");
		mv.setViewName("save_result");
		return mv;
	}

	
	/**
	 * 列表
	 */
	private List<PageData> sList;
	@RequestMapping
	public ModelAndView list(HttpSession session, Page page)throws Exception{
		mv.clear();
		pd.clear();
		pd = this.getPageData();
		String PARENT_ID = pd.getString("PARENT_ID");
		if (StringUtils.isBlank(PARENT_ID)) {// 判断父id为空则设0
			PARENT_ID = "0";
			pd.put("PARENT_ID", PARENT_ID);
		}
		
		
		if(null != PARENT_ID && !"".equals(PARENT_ID) && !"0".equals(PARENT_ID)){
			
			//返回按钮用
			PageData pdp = new PageData();
			pdp = this.getPageData();
			
			pdp.put("ORGAN_ID", PARENT_ID);
			pdp = organService.getOrganById(pdp);
			mv.addObject("pdp", pdp);
			
			//头部导航
			sList = new ArrayList<PageData>();
			this.getZDname(PARENT_ID);	//	逆序
			Collections.reverse(sList);
			
		}
		
		
		String NAME = pd.getString("ORGAN_NAME");
		if(null != NAME && !"".equals(NAME)){
			NAME = NAME.trim();
			pd.put("ORGAN_NAME", NAME);
		}
		
		page.setPd(pd);				
		List<PageData> varList = organService.organlistPage(page);
		
		mv.setViewName("system/organ/organ_list");
		mv.addObject("varList", varList);
		mv.addObject("varsList", sList);
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	//递归
	public void getZDname(String PARENT_ID) {
//		logBefore(logger, "递归");
		try {
			
			PageData pdps = new PageData();
			pdps.put("ORGAN_ID", PARENT_ID);
			pdps = organService.getOrganById(pdps);
			if(pdps != null){
				sList.add(pdps);
				String PARENT_IDs = pdps.getString("PARENT_ID");
				this.getZDname(PARENT_IDs);
			}
		} catch (Exception e) {
			logger.error(e.toString(),e);
		}
	}
	
	/**
	 * 去新增页面
	 */
	@RequestMapping(value="/toAdd")
	public ModelAndView toAdd(Page page){
		try{
			
			pd = this.getPageData();
			mv.setViewName("system/organ/organ_edit");
			mv.addObject("pd", pd);
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
		return mv;
	}
	
	
	
	/**
	 * 去编辑页面
	 */
	@RequestMapping(value="/toEdit")
	public ModelAndView toEdit( String ROLE_ID )throws Exception{
 
		pd = this.getPageData();
	
		pd = organService.getOrganById(pd);
		
		
		mv.setViewName("system/organ/organ_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	
	/**
	 * 删除用户
	 */
	@RequestMapping(value="/del")
	public void del(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			String ORGAN_ID = pd.getString("ORGAN_ID");
			
			if(!organService.listSubOrganByParentId(ORGAN_ID).isEmpty()){
				out.write("false");
			}else{
				organService.deleteOrganById(ORGAN_ID);
				out.write("success");
			}
			
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	

}
