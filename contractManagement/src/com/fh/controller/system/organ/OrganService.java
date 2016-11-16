package com.fh.controller.system.organ;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Organ;
import com.fh.util.PageData;

@Service("organService")
public class OrganService {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	/**
	 * 查所有顶级节点
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Organ> listAllTopOrgan() throws Exception {
		return (List<Organ>) dao.findForList("OrganMapper.listAllTopOrgan", null);
	}

	/**
	 * 查询节点下的所有子节点-递归
	 * 
	 * @param parentId
	 *        根节点id 传0为所有
	 * @return
	 * @throws Exception
	 */
	public List<Organ> listTreeOrgan(String parentId) throws Exception {
		List<Organ> organsTree = (List<Organ>) dao.findForList("OrganMapper.listTreeOrgan", parentId);
		return organsTree;
	}

	/**
	 * 某节点下的子节点
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Organ> listSubOrganByParentId(String parentId) throws Exception {
		return (List<Organ>) dao.findForList("OrganMapper.listSubOrganByParentId", parentId);
	}

	/**
	 * id查单个对象
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getOrganById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrganMapper.getOrganById", pd);
	}

	/**
	 * 编辑
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData) dao.findForObject("OrganMapper.updateOrgan", pd);
	}

	/**
	 * 删除当前节点与其子节点
	 * 
	 * @param ORGAN_ID
	 * @throws Exception
	 */
	public void deleteOrganById(String ORGAN_ID) throws Exception {
		dao.save("OrganMapper.deleteOrganById", ORGAN_ID);
	}

	/**
	 * 取最大id
	 * 
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String findMaxId(PageData pd) throws Exception {
		return (String) dao.findForObject("OrganMapper.findMaxId", pd);

	}

	/**
	 * 新增
	 * 
	 * @param organ
	 * @throws Exception
	 */
	public void saveOrgan(PageData pd) throws Exception {
		dao.save("OrganMapper.insertOrgan", pd);
	}
	/*
	 * ----------------------- new -------------------------------
	 */
	
	public List<PageData> organlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("OrganMapper.organlistPage", page);
	}
}
