package com.fh.controller.system.user;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.PageData;


@Service("userService")
public class UserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	//======================================================================================
	
	/*
	* 通过id获取数据
	*/
	public PageData findByUiId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserXMapper.findByUiId", pd);
	}
	/*
	* 通过loginname获取数据
	*/
	public PageData findByUId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserXMapper.findByUId", pd);
	}
	/*
	* 保存用户
	*/
	public void saveU(PageData pd)throws Exception{
		dao.save("UserXMapper.saveU", pd);
	}
	/*
	* 修改用户
	*/
	public void editU(PageData pd)throws Exception{
		dao.update("UserXMapper.editU", pd);
	}
	/*
	* 删除用户
	*/
	public void deleteU(PageData pd)throws Exception{
		dao.update("UserXMapper.deleteU", pd);
	}
	/*
	*用户列表(用户组)
	*/
	public List<PageData> listPdPageUser(Page page)throws Exception{
		return (List<PageData>) dao.findForList("UserXMapper.userlistPage", page);
	}
	
	/*
	*用户列表(供应商用户)
	*/
	public List<PageData> listGPdPageUser(Page page)throws Exception{
		return (List<PageData>) dao.findForList("UserXMapper.userGlistPage", page);
	}
	/*
	* 保存用户IP
	*/
	public void saveIP(PageData pd)throws Exception{
		dao.update("UserXMapper.saveIP", pd);
	}
	
	/*
	* 登录判断
	*/
	public PageData getUserByNameAndPwd(PageData pd)throws Exception{
		return (PageData)dao.findForObject("UserXMapper.getUserInfo", pd);
	}
	/*
	* 跟新登录时间
	*/
	public void updateLastLogin(PageData pd)throws Exception{
		dao.update("UserXMapper.updateLastLogin", pd);
	}
	//======================================================================================
	
//	public User getUserById(Integer userId) throws Exception {
//		
//		return (User) dao.findForObject("UserMapper.getUserById", userId);
//	}

//	public boolean insertUser(User user) throws Exception {
//		
//		Integer count = (Integer) dao.findForObject(
//							"UserMapper.getCountByName", 
//							user.getUSERNAME()
//						);
//		if(count>0){
//			return false;
//		}else{
//			dao.save("UserMapper.insertUser", user);
//			return true;
//		}
//	}

//	public List<User> listPageUser(User user) throws Exception{
//		
//		return (List<User>) dao.findForList("UserMapper.listPageUser", user);
//	}
//
//	public List<User> tclistPageUser(User user) throws Exception{
//		
//		return (List<User>) dao.findForList("UserMapper.tclistPageUser", user);
//	}
	
//	public void updateUser(User user) throws Exception {
//		
//		dao.update("UserMapper.updateUser", user);
//	}
//
//	public void updateUserBaseInfo(User user) throws Exception{
//		
//		dao.update("UserMapper.updateUserBaseInfo", user);
//	}
//	
//	public void updateUserRights(User user) throws Exception{
//		
//		dao.update("UserMapper.updateUserRights", user);
//	}
//	
//	
//	public void deleteUser(int userId) throws Exception{
//		
//		dao.delete("UserMapper.deleteUser", userId);
//	}

	//通过id获取数据
	public User getUserAndRoleById(BigDecimal USER_ID) throws Exception {
		
		return (User) dao.findForObject("UserMapper.getUserAndRoleById", USER_ID);
		
	}


//	public List<User> listAllUser() throws Exception {
//		
//		return (List<User>) dao.findForList("UserMapper.listAllUser", null);
//		
//	}
	
	/**
	 * 添加
	 */
//	public void add(PageData pd) throws Exception {
//		dao.findForList("UserMapper.insert", pd);
//	}
//
//	public User getUserById2(String username) throws Exception {
//		return (User) dao.findForObject("UserMapper.getUserById2", username);
//	}
	
}
