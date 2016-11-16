package com.fh.controller.web.product.partners;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.PageData;

/**
 * 后台合作伙伴
 * @author Administrator
 */
@Controller
@RequestMapping(value="/partners")
public class partnersController  extends BaseController{

	@Resource(name = "partnersService")
	private partnersService partnersService;
	
	
	/**
	 * 显示合作伙伴信息
	 */
	@RequestMapping
	public ModelAndView aboutUs() throws Exception {
		List<PageData> list = partnersService.partners();
		mv.setViewName("web/product/partners/partners_list");// 页面
		mv.addObject("pd", pd);
		mv.addObject("list",list);
		return mv;
	}
	
	/**
	 * 请求新增页面
	 */
	@RequestMapping(value = "/add")
	public ModelAndView toAdd() throws Exception {
		mv.setViewName("web/product/partners/partners_add");
		
		return mv;
	}
	
	/**
	 * 新增录入
	 */
	@RequestMapping(value = "toAdd", method = RequestMethod.POST)
	public ModelAndView add() throws Exception {
		try {
			pd = this.getPageData();
			pd.put("id", this.get32UUID());
			partnersService.add(pd);
			
			mv.addObject("msg", "success");
			mv.setViewName("save_result");
		} catch (Exception e) {
			logger.error(e.toString(), e);
			mv.addObject("msg", "failed");
		}
		
		return mv;
	}
	
	/**
	 * 跳转修改页面
	 */
	@RequestMapping(value = "/edit")
	public ModelAndView toEdit() throws Exception {
		pd = this.getPageData();
		pd = partnersService.listpartnersById(pd);
		
		mv.setViewName("web/product/partners/partners_edit");
		mv.addObject("pd", pd);
		
		return mv;
	}
	
	/**
	 * 修改内容
	 */
	@RequestMapping(value = "/update")
	public ModelAndView updateDict() throws Exception {
		pd = this.getPageData();
		partnersService.update(pd);
		
		mv.addObject("msg", "success");
		mv.setViewName("save_result");
		
		return mv;
	}
	
	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete")
	public void deleteDict(PrintWriter out) throws Exception {
		pd = this.getPageData();
		partnersService.delete(pd);
		mv.setViewName("save_result");
		
		out.write("success");
		out.close();
	}
}
