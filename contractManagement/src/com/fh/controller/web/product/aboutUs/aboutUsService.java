package com.fh.controller.web.product.aboutUs;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("aboutUsService")
public class aboutUsService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	public List<PageData> aboutUs() throws Exception {
		
		return (List<PageData>) dao.findForList("aboutUsMapper.aboutUs", null);
	}
	
	public void add(PageData pd) throws Exception {
		 dao.save("aboutUsMapper.add", pd);
	}
	
	public PageData listaboutUsById(PageData pd) throws Exception {
		
		return (PageData) dao.findForObject("aboutUsMapper.listaboutUsById", pd);
	}
	
	public void delete(PageData pd)  throws Exception {
		 dao.delete("aboutUsMapper.delete", pd);
	}
	
	public void update(PageData pd) throws Exception {
		
		dao.findForObject("aboutUsMapper.update", pd);
	}
}
