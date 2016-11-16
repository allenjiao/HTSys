package com.fh.controller.system.role;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.system.Role;
import com.fh.util.PageData;

@Service("roleService")
public class RoleService{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public List<Role> listAllERRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllERRoles", null);
		
	}
	
	
	public List<Role> listAllappERRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllappERRoles", null);
		
	}
	
	public List<Role> listAllERGRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllERGRoles", null);
		
	}
	
	public List<Role> listAllRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRoles", null);
		
	}
	
	public List<Role> listGRoles() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listGRoles", null);
		
	}
	
	//通过当前登录用的角色id获取管理权限数据
	public PageData findGLbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findGLbyrid", pd);
	}
	
	//通过当前登录用的角色id获取用户权限数据
	public PageData findYHbyrid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("RoleMapper.findYHbyrid", pd);
	}
	
	//列出此角色下的所有用户
	public List<PageData> listAllUByRid(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllUByRid", pd);
		
	}
	
	/**
	 * 列出此部门的所有下级
	 */
	public List<Role> listAllRolesByPId(PageData pd) throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesByPId", pd);
		
	}
	
	public List<Role> listAllRolesTravel() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesTravel", null);
		
	}
	
	public List<Role> listAllRolesAgencies() throws Exception {
		return (List<Role>) dao.findForList("RoleMapper.listAllRolesAgencies", null);
		
	}
	
	//列出客服权限表里的数据 
	public List<PageData> listAllkefu(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllkefu", pd);
	}
	
	//列出供应商权限表里的数据 
	public List<PageData> listAllGysQX(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("RoleMapper.listAllGysQX", pd);
	}
	
	//删除客服权限表里对应的数据
	public void deleteKeFuById(String roleId) throws Exception {
		dao.delete("RoleMapper.deleteKeFuById", roleId);
		
	}
	
	public void deleteRoleById(String ROLE_ID) throws Exception {
		dao.delete("RoleMapper.deleteRoleById", ROLE_ID);
		
	}

	public Role getRoleById(String roleId) throws Exception {
		return (Role) dao.findForObject("RoleMapper.getRoleById", roleId);
		
	}

	public boolean insertRole(Role role) throws Exception {
		
		Integer count = (Integer) dao.findForObject("RoleMapper.getCountByName", role);
		if(count>0)
			return false;
		else{
			dao.save("RoleMapper.insertRole", role);
			return true;
		}
		
	}

	public boolean updateRoleBaseInfo(Role role) throws Exception {
		
		Integer count = (Integer) dao.findForObject("RoleMapper.getCountByName", role);
		
		if(count>0)
			return false;
		else{
			dao.update("RoleMapper.updateRoleBaseInfo", role);
			return true;
		}
		
	}
	
	public void updateRoleRights(Role role) throws Exception {
		
		dao.update("RoleMapper.updateRoleRights", role);
		
	}
	
	/**
	 * 权限(增删改查)
	 */
	public void updateQx(String msg,PageData pd) throws Exception {
		dao.update("RoleMapper."+msg, pd);
	}
	
	/**
	 * 客服权限
	 */
	public void updateKFQx(String msg,PageData pd) throws Exception {
		dao.update("RoleMapper."+msg, pd);
	}
	
	/**
	 * 供应商c权限
	 */
	public void gysqxc(String msg,PageData pd) throws Exception {
		dao.update("RoleMapper."+msg, pd);
	}
	
	/**
	 * 给全部子职位加菜单权限
	 */
	public void setAllRights(PageData pd) throws Exception {
		dao.update("RoleMapper.setAllRights", pd);
		
	}
	
	/**
	 * 添加
	 */
	public void add(PageData pd) throws Exception {
		dao.findForList("RoleMapper.insert", pd);
	}
	
	/**
	 * 保存客服权限
	 */
	public void saveKeFu(PageData pd) throws Exception {
		dao.findForList("RoleMapper.saveKeFu", pd);
	}
	
	/**
	 * 保存供应商权限
	 */
	public void saveGYSQX(PageData pd) throws Exception {
		dao.findForList("RoleMapper.saveGYSQX", pd);
	}
	
	/**
	 * 通过id查找
	 */
	public PageData findObjectById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RoleMapper.findObjectById", pd);
	}
	
	/**
	 * 编辑角色
	 */
	public PageData edit(PageData pd) throws Exception {
		return (PageData)dao.findForObject("RoleMapper.edit", pd);
	}

}
