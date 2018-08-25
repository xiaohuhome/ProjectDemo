package com.xiaohu.demo.utils.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * <p>标题: JerseyUtil.java</p>
 * <p>业务描述:基层医疗卫生开发平台</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2013</p>
 * @author blank
 * @date 2018年4月24日 
 * @version V1.0  
 */
public class JerseyUtil {
	
	/** 
	 * 方法名:          get
	 * 方法功能描述:    	get方式调用对方接口
	 * @param:         
	 * @return:        
	 * @Author:        blank
	 * @Create Date:   2016年12月6日 下午5:29:35
	 */
	public String get(String url) {
		return null;
	}
	
	@SuppressWarnings({ "resource", "deprecation" })
	public static String post(String url, String postBody) {
		StringEntity s = null;
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		HttpResponse responsePost = null;
		HttpEntity entity = null;	
		String xmlContent = null;
		
		try {
			s = new StringEntity(postBody ,"UTF-8");
			s.setContentType("application/json");
			
			httpPost = new HttpPost(url);
			httpPost.setEntity(s);	
			httpClient = new DefaultHttpClient();
			responsePost = httpClient.execute(httpPost);
			entity = responsePost.getEntity();
			xmlContent = EntityUtils.toString(entity,"UTF-8");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return xmlContent;
	}	
}
