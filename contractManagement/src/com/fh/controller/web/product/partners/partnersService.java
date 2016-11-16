package com.fh.controller.web.product.partners;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("partnersService")
public class partnersService {
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	public List<PageData> partners() throws Exception {
		
		return (List<PageData>) dao.findForList("partnersMapper.partners", null);
	}

	public void add(PageData pd) throws Exception {
		dao.findForList("partnersMapper.add", pd);
	}

	public PageData listpartnersById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("partnersMapper.listpartnersById", pd);
	}

	public void update(PageData pd) throws Exception {
		dao.findForList("partnersMapper.update", pd);
	}

	public void delete(PageData pd) throws Exception {
		dao.findForList("partnersMapper.delete", pd);
	}
}
