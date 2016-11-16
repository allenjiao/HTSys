package com.fh.util;

import java.io.IOException;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;



import org.dom4j.Document;   
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   

/**
 * 通过短信接口发送短信
 */
public class sendsmsUtil {
	
	private static String Url = "http://106.ihuyi.com/webservice/sms.php?method=Submit";
	
	
	public static void main(String [] args) {
		
//		sendSms(String mobile,String code);
//		sendSmsAll(List<PageData> list)
	}
	
	/**
	 * 给一个人发送单条短信
	 * @param mobile 手机号
	 * @param code  验证码
	 */
	public static void sendSms(String mobile,String code){
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod(Url); 
			
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");

	    String content = new String("您的验证码是："+code+"。请不要把验证码泄露给其他人。");  
	    
		NameValuePair[] data = {//提交短信
		    new NameValuePair("account", "cf_qdlsdw"), 
		    new NameValuePair("password", "5M9sR6"), 	//密码可以使用明文密码或使用32位MD5加密
		    new NameValuePair("mobile", mobile), 
		    new NameValuePair("content", content),
		};
		
		method.setRequestBody(data);
		
		try {
			client.executeMethod(method);
			
			String SubmitResult =method.getResponseBodyAsString();
					
			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();


			code = root.elementText("code");
			String msg = root.elementText("msg");
			String smsid = root.elementText("smsid");
			
			
			System.out.println(code);
			System.out.println(msg);
			System.out.println(smsid);
			
			if(code == "2"){
				System.out.println("短信提交成功");
			}
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}	
		
	}
	
	/**
	 * 给多个人发送单条短信
	 * @param list 手机号验证码
	 */
	public static void sendSmsAll(List<PageData> list){
		String code;
		String mobile;
		for(int i=0;i<list.size();i++){
			code=list.get(i).get("code").toString();
			mobile=list.get(i).get("mobile").toString();
			
			sendSms(mobile,code);
		}
	}
	
}

