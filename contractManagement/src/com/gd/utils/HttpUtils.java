package com.gd.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * http 简化工具
 * 
 * @author reny<br>
 *         创建时间：2016-5-10下午3:40:11
 */
public class HttpUtils {
	private static Logger log = Logger.getLogger(HttpUtils.class);

	/**
	 * post
	 * 
	 * @param urls
	 * @param param
	 * @param map
	 *        heads<"","">
	 * @return
	 * @throws IOException
	 */
	public static String post(String urls, String param, Map<String, String> map) throws IOException {
		// 组装参数
		log.debug(urls + "?postContent=" + param);
		URL url = new URL(urls);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("POST");
		connection.setConnectTimeout(10 * 1000);
		connection.setReadTimeout(10 * 1000);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setRequestProperty("Accept", "application/json");
		if (map != null && !map.isEmpty()) {
			for (String str : map.keySet()) {
				connection.setRequestProperty(str, map.get(str));
			}
		}
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
		out.write(param);
		out.flush();
		out.close();
		String strLine = "";
		StringBuffer strResponse = new StringBuffer();
		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
		while ((strLine = reader.readLine()) != null) {
			strResponse.append(strLine);
		}
		log.debug(strResponse.toString());
		return strResponse.toString();
	}

	public static String get(String urls, String param) throws IOException {
		URL url = new URL(urls + "?" + param);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestMethod("GET");
		String strLine = "";
		StringBuffer strResponse = new StringBuffer();
		InputStream in = connection.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		while ((strLine = reader.readLine()) != null) {
			strResponse.append(strLine);
		}
		return strResponse.toString();
	}

	public static void main(String[] args) {
		try {
//			String resStr = post("http://10.10.21.50:8082/oauth2/auth/code.do",
//					"{\"appid\":\"1234\",\"response_type\":\"code\"}");
			String resStr = post("http://10.10.21.50:8082/APIMModule/auth/code.do",
					"{\"appid\":\"8337\",\"response_type\":\"code\"}", null);
			log.info(resStr);
			log.info(args.length);
		} catch (Exception e) {
			log.error(e);
		}
	}
}
