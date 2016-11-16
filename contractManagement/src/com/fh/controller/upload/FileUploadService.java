package com.fh.controller.upload;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;

@Service("fileUploadService")
public class FileUploadService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	
	public void saveFile(PageData pd) throws Exception{
		dao.save("UploadFileMapper.saveUpload", pd);
	}
	
	public Integer savePicture(PageData pd) throws Exception{
		return (Integer)dao.save("UploadFileMapper.saveUpload", pd);
	}

}
