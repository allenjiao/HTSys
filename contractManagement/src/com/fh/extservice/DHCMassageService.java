package com.fh.extservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fh.util.Config;
import com.gd.utils.HttpUtils;

@Service("DHCMassageService")
public class DHCMassageService {
	private static final Logger log = Logger.getLogger(DHCMassageService.class);

	private static String baseUrl = "http://10.10.21.50:8082/";

	private static String appid = "1463040691707";
	private static String appsecretyid = "123456";
	private static String response_type = "code";
	private static String grant_type = "authorization_code";

	private static String codeAndCheck() {
		String code = null;
		try {
			JSONObject obj = new JSONObject();
			obj.put("appid", appid);
			obj.put("response_type", response_type);
			String resStr = HttpUtils.post(baseUrl + "APIMModule/auth/code.do", obj.toString(), null);
			JSONObject resObj = JSONObject.fromObject(resStr);
			if (StringUtils.equalsIgnoreCase("00", resObj.getString("result"))) {
				code = resObj.getString("code");
			} else {
				log.warn(resObj.getString("resultdesc"));
				return null;
			}
		} catch (IOException e) {
			log.error(e);
		}
		String access_token = null;
		try {
			JSONObject obj = new JSONObject();
			obj.put("appsecretyid", appsecretyid);// 第三方商户密码
			obj.put("code", code); // 授权码
			obj.put("grant_type", grant_type);
			String resStr = HttpUtils.post(baseUrl + "APIMModule/auth/codecheck.do", obj.toString(), null);
			JSONObject resObj = JSONObject.fromObject(resStr);
			if (StringUtils.equalsIgnoreCase("00", resObj.getString("result"))) {
				access_token = resObj.getString("access_token");
			} else {
				log.warn(resObj.getString("resultdesc"));
				return null;
			}
		} catch (Exception e) {
			log.error(e);
		}
		return access_token;
	}

	public static void sendMassage(String smscontent) {
		JSONObject obj = new JSONObject();
		JSONObject commonInfoJson = new JSONObject();
		commonInfoJson.put("visittype", "AF"); // 访问类型
		commonInfoJson.put("operation", "ADD");// 操作

		JSONObject commandInfoJson = new JSONObject();
		commandInfoJson.put("smscontent", smscontent);
		commandInfoJson.put("rank", "normal"); // 优先级
		List<String> phonenumbers = new ArrayList<String>();
		String phonenumbersStr = Config.getStr("phonenumbers");
		String[] strs = phonenumbersStr.split(";");
		commandInfoJson.put("sendnum", strs.length);
		for (int i = 0; i < strs.length; i++) {

			phonenumbers.add(strs[i]);
		}
		commandInfoJson.put("senduserlist", phonenumbers);
		obj.put("commoninfo", commonInfoJson);
		obj.put("commandinfo", commandInfoJson);
		Map<String, String> headMap = new HashMap<String, String>();
		headMap.put("accessToken", codeAndCheck());
		try {
			String resStr = HttpUtils.post(baseUrl + "APIMModule/apim/api.do", obj.toString(), headMap);
			JSONObject resObj = JSONObject.fromObject(resStr);
			if (StringUtils.equalsIgnoreCase("00", resObj.getString("result"))) {
				log.info(resObj.toString());
			} else {
				log.warn(resObj.getString("resultdesc"));
			}
		} catch (Exception e) {
			log.error(e);
		}
	}

	public static void main(String[] args) {
		sendMassage("hi\nbadday!fuck");
	}
}
