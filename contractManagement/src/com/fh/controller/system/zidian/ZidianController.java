package com.fh.controller.system.zidian;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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
@RequestMapping(value="/zidian")
public class ZidianController extends BaseController {
	
	@Resource(name="zidianService")
	private ZidianService zidianService;
	

	
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
		pdp.put("ZD_ID", PARENT_ID);
		
		if(null == pd.getString("ZD_ID") || "".equals(pd.getString("ZD_ID"))){
			if(null != PARENT_ID && "0".equals(PARENT_ID)){
				pd.put("JB", 1);
				pd.put("P_BM", pd.getString("BIANMA"));
			}else{
				pdp = zidianService.findById(pdp);
				pd.put("JB", Integer.parseInt(pdp.get("JB").toString()) + 1);
				pd.put("P_BM", pdp.getString("BIANMA") + "_" + pd.getString("BIANMA"));
			}
			pd.put("ZD_ID", this.get32UUID());	//ID
			zidianService.save(pd);
		}else{
			pdp = zidianService.findById(pdp);
			if(null != PARENT_ID && "0".equals(PARENT_ID)){
				pd.put("P_BM",  pd.getString("BIANMA"));
			}else{
				pd.put("P_BM", pdp.getString("BIANMA") + "_" + pd.getString("BIANMA"));
			}
			
			zidianService.edit(pd);
		}
		
		
		mv.addObject("msg","ok");
		mv.setViewName("save_result");
		return mv;
	}

	
	/**
	 * 列表
	 */
	List<PageData> szdList;
	@RequestMapping
	public ModelAndView list(HttpSession session, Page page)throws Exception{
		mv.clear();
		pd.clear();
		pd = this.getPageData();
		String PARENT_ID = pd.getString("PARENT_ID");
		
		
		if(null != PARENT_ID && !"".equals(PARENT_ID) && !"0".equals(PARENT_ID)){
			
			//返回按钮用
			PageData pdp = new PageData();
			pdp = this.getPageData();
			
			pdp.put("ZD_ID", PARENT_ID);
			pdp = zidianService.findById(pdp);
			mv.addObject("pdp", pdp);
			
			//头部导航
			szdList = new ArrayList<PageData>();
			this.getZDname(PARENT_ID);	//	逆序
			Collections.reverse(szdList);
			
		}
		
		
		String NAME = pd.getString("NAME");
		if(null != NAME && !"".equals(NAME)){
			NAME = NAME.trim();
			pd.put("NAME", NAME);
		}
		
		page.setPd(pd);				
		List<PageData> varList = zidianService.zidianlistPage(page);
		
		mv.setViewName("system/zidian/zd_list");
		mv.addObject("varList", varList);
		mv.addObject("varsList", szdList);
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	//递归
	public void getZDname(String PARENT_ID) {
		logBefore(logger, "递归");
		try {
			
			PageData pdps = new PageData();;
			pdps.put("ZD_ID", PARENT_ID);
			pdps = zidianService.findById(pdps);
			if(pdps != null){
				szdList.add(pdps);
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
			mv.setViewName("system/zidian/zd_edit");
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
	
		pd = zidianService.findById(pd);
		
		if(Integer.parseInt(zidianService.findCount(pd).get("ZS").toString()) != 0){
			mv.addObject("msg", "no");
		}else{
			mv.addObject("msg", "ok");
		}
		
		
		mv.setViewName("system/zidian/zd_edit");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
	/**
	 * 判断编码是否存在
	 */
	@RequestMapping(value="/has")
	public void has(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			if(zidianService.findBmCount(pd) != null){
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
	 * 删除用户
	 */
	@RequestMapping(value="/del")
	public void del(PrintWriter out){
		mv.clear();
		try{
			pd = this.getPageData();
			
			if(Integer.parseInt(zidianService.findCount(pd).get("ZS").toString()) != 0){
				out.write("false");
			}else{
				zidianService.delete(pd);
				out.write("success");
			}
			
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
		
	}
	
	
	

}
