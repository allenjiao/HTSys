package com.gd.utils;


import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Json转换工具
 * 
 * @author tyutNo4 修改时间 2013年6月6日11:52:10
 */

public class JsonUtil {
	private static final Logger log = Logger.getLogger(JsonUtil.class);
	private static ObjectMapper mapper;
	static {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	/**
	 * 如果对象为Null,返回"null". 如果集合为空集合,返回"[]".
	 */
	public static String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			log.warn("write to json string error:" + object, e);
			return null;
		}
	}
	
	/**
	 * 如果JSON字符串为Null或"null"字符串,返回Null. 如果JSON字符串为"[]",返回空集合.
	 * 
	 * 如需读取集合如List/Map,且不是List<String>这种简单类型时使用如下语句: List<MyBean> beanList =
	 * binder.getMapper().readValue(listString, new
	 * TypeReference<List<MyBean>>() {});
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> T fromJson(String jsonString, Class<T> clazz) throws NullPointerException, IOException {
		if (StringUtils.isEmpty(jsonString)) {
			throw new NullPointerException();
		}
		return mapper.readValue(jsonString, clazz);
	}
	
	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
	
}
