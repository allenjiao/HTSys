package com.fh.controller.system.head;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fh.controller.base.BaseController;
import com.fh.controller.system.user.UserService;
import com.fh.util.AppUtil;
import com.fh.util.PageData;

/** 
 * 类名称：HeadController
 * 创建人：fuhang 
 * 创建时间：2014年8月16日
 * @version
 */
@Controller
@RequestMapping(value="/head")
public class HeadController extends BaseController {
	
	@Resource(name="userService")
	private UserService userService;	

	
	
	//获取头部信息
	@RequestMapping(value="/getUname")
	@ResponseBody
	public Object getList(HttpSession session) {
		
		Map map = new HashMap();
		try {
			pd = this.getPageData();
			List<PageData> pdList = new ArrayList<PageData>();
			
			String USERNAME = session.getAttribute("USERNAME").toString();											//获取当前登录者loginname
			pd.put("USERNAME", USERNAME);
			
			PageData pds = new PageData();
			pds = userService.findByUId(pd);
			
			pdList.add(pds);
			map.put("list", pdList);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		} finally {
			logAfter(logger);
		}
		return AppUtil.returnObject(pd, map);
	}


	
}
