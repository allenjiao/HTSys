package com.fh.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * 获取properties配置文件的内容
 * 
 * @author xiao_hb 修改时间 2012-8-8
 */
/**
 * 
 * @author reny<br>
 *         创建时间：2015-3-18下午4:59:00
 */
public class Config {
	private static Properties prop;
	private static String file_Path = "";
	private static Logger log = Logger.getLogger(Config.class);
	static {
//		String systemRuntimeMode  = System.getProperty("system.runtime.mode");
//		if(systemRuntimeMode==null){
//			throw new MissingResourceException("Not set [system.runtime.mode],please config it in [server->java->procedure->JVM->custom attribute].",Config.class.getName(),"system.runtime.mode");
//		}
//		log.info("系统运行环境："+systemRuntimeMode);
//		file_Path="configuration_"+systemRuntimeMode+".properties";
		file_Path = "config.properties";
		Config.afresh();
//		log.info("配置文件环境："+Config.getStr("Dsystem.runtime.mode"));
	}

//	public static String getSystemRuntimeMode() {
//		return null;
//	}

	public static void afresh() {
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			URL url = cl.getResource(file_Path);
			if (url == null) {
				throw new MissingResourceException(
						file_Path
								+ " is not found , set [system.runtime.mode] is error,please config it in [server->java->procedure->JVM->custom attribute].",
						Config.class.getName(), "system.runtime.mode");
			}
			InputStream inputStream = cl.getResourceAsStream(file_Path);
			prop = new Properties();
			prop.load(inputStream);
			inputStream.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static String getStr(String key) {
		String str = Config.prop.getProperty(key);
		return str == null ? "" : str;
	}

	public static void setStr(String key, String value) {
		try {
			// 文件输出流
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			URL url = cl.getResource(Config.file_Path);
			String filePath = java.net.URLDecoder.decode(url.getFile(), "utf-8");
			FileOutputStream fos = new FileOutputStream(filePath);
			// 将Properties集合保存到流中
			prop.setProperty(key, value);
			prop.store(fos, "Copyright (c) rr Studio");
			// 关闭流
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
