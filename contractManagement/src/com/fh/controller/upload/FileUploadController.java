package com.fh.controller.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.util.PathUtil;
import com.fh.util.UuidUtil;

/**
 * 上传
 */
@Controller
@RequestMapping(value="/uploadfile")
public class FileUploadController extends BaseController {

	@Resource(name = "fileUploadService")
	private FileUploadService fileUploadService;

	@RequestMapping(value = "/add")
	public ModelAndView add() throws Exception {
		mv.setViewName("uploadfile/oneFileUpload");
		
		return mv;
	}

	/**
	 * 图片上传
	 * 
	 * @param request
	 * @param name
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public ModelAndView handleFormUpload(HttpServletRequest request,@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file) throws Exception {
		
		pd = this.getPageData();
		
		String pictureSaveFilePath  = PathUtil.getPicturePath("save",  "business/businessLicense");
		String pictureVisitFilePath = PathUtil.getPicturePath("visit", "business/businessLicense");
		
		if (!file.isEmpty()) {
			try {
				String id = UuidUtil.get32UUID();
				this.copyFile(file.getInputStream(), pictureSaveFilePath,id+".jpg").replaceAll("-", "");
				
				String path = pictureVisitFilePath+id+".jpg";
				pd.put("id", id);
				pd.put("path", path);
				pd.put("type",001);
				pd.put("picture_id",100);
				pd.put("explanation", "");
				
				fileUploadService.saveFile(pd);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		String pathaddress = PathUtil.PathAddress();
		pd.get(pathaddress+"path");
		pd.put("path", pathaddress+pd.get("path"));
		
		mv.setViewName("uploadfile/success");
		mv.addObject("pd", pd);
		
		return mv;
	}
	


	/**
	 * 文件下载
	 * 
	 * @param response
	 * @param request
	 * @param realName
	 * @param name
	 */
	@RequestMapping("/download/{realName}/{name}")
	public void download(HttpServletResponse response,
			HttpServletRequest request, @PathVariable String realName,
			@PathVariable String name) {
		OutputStream os = null;
		response.reset();
		realName = request.getSession().getServletContext().getRealPath("/")
				+ "upload" + File.separator + realName;
		response.setHeader("Content-Disposition", "attachment; filename="
				+ name);
		response.setContentType("application/octet-stream; charset=utf-8");
		try {
			os = response.getOutputStream();
			os.write(FileUtils.readFileToByteArray(new File(realName)));
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 写文件到当前目录的upload目录中
	 * 
	 * @param in
	 * @param fileName
	 * @throws IOException
	 */
	private String copyFile(InputStream in, String dir, String realName)
			throws IOException {
		File file = new File(dir, realName);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		FileUtils.copyInputStreamToFile(in, file);
		return realName;
	}

	// @RequestMapping(value="/upload",method=RequestMethod.POST)
	// public String upload(@RequestParam("imageFile") MultipartFile
	// image,HttpServletRequest request) throws IOException
	// {
	// String path = request.getRealPath("/upload");
	// request.setAttribute("name",image.getOriginalFilename());
	// request.setAttribute("img","<img src='http://mn960mn.blog.163.com/blog/upload/"+image.getOriginalFilename()+"'></img>");
	// FileCopyUtils.copy(image.getBytes(),new
	// File(path+"/"+image.getOriginalFilename()));
	// return "ok";
	// }
	// @RequestMapping(value = "updatecenter", method = RequestMethod.POST)
	// public String updateUserCenter(MultipartHttpServletRequest request,
	// Account account, Model model) { //注这里用的是MultipartHttpServletRequest
	// // 获得上传证件的扫描件
	// MultipartFile businessFile = request.getFile("businessfile");
	// String flag = null;
	// if (null != businessFile && 0 != businessFile.getSize()) {//判断是不是空
	//
	// /**
	// *saveCenterFile为上传文件自己写的个工具类，主要完成文件上传，并返回上传后的文件完整路径
	// *参数需要说明的是：（1）account.getBusiness_file()，为上次文件完整路径（修改文件时使用的）
	// * （2）account是新文件将要保存的文件夹拼接
	// *
	// **/
	// flag = FileUtil.saveCenterFile(request.getSession()
	// .getServletContext().getRealPath(""), businessFile,
	// account.getBusiness_file(),"account");
	// if (flag == "false") {
	// model.addAttribute("resultmsg",
	// "<font color='red'>修改失败,请上传jpg或gif格式的图片!</font>");
	// return "mycenter/resource_user";
	// } else {
	// account.setBusiness_file(flag); //若上传成功，则将新文件完整路径保持在model中，以便持久话到数据库
	// }
	// }
	// return "redirect:/mycenter/interaccount/resource_user?result=yes";
	// }
}