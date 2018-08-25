package com.xiaohu.demo.utils.http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import com.xiaohu.demo.utils.properties.ConstantUtils;

/**
 * <p>标题: HttpClientUtil.java</p>
 * <p>业务描述:基层医疗卫生开发平台</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2013</p>
 * @author blank
 * @date 2016年12月6日
 * @version V1.0 
 */
public class HttpClientUtil {
	
	/** 
	 * 方法名:          get
	 * 方法功能描述:    	get方式调用对方接口
	 * @param:         
	 * @return:        
	 * @Author:        blank
	 * @Create Date:   2016年12月6日 下午5:29:35
	 */
	public static String get(String url) {
    String responseJson = null;
		HttpClient httpClient = new DefaultHttpClient();

    HttpGet httpGet = new HttpGet(url);    
    HttpResponse response = null;
    
		try {
			response = httpClient.execute(httpGet);
			
			if(response.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				HttpEntity entity = response.getEntity();
	    
		    if (entity != null) {    
		      InputStream instreams = entity.getContent();    
		      responseJson = convertStreamToString(instreams);  
		      httpGet.abort(); 
//		      System.out.println("===" + url + "==" + responseJson + "==");
		    }
			}
		} 
		catch (ClientProtocolException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} 
		
		return responseJson;
	}
	
	public static String post(String url, String json) {
		String responseJson = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		
		try {
			StringEntity s = new StringEntity(json);
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			httpPost.setEntity(s);
			 
			HttpResponse res = client.execute(httpPost);
			
			if(res.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				HttpEntity entity = res.getEntity();

		    if (entity != null) {    
		      InputStream instreams = entity.getContent();    
		      responseJson = convertStreamToString(instreams);  
		      httpPost.abort(); 
//		      System.out.println("===" + url + "==" + responseJson + "==");
		    }				
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return responseJson;
	}	
	
	public static String uploadFile(File[] file, String[] fileName, String bType, String bId) {
		String res = null;
		HttpClient httpClient = null;
		HttpResponse httpResponse = null;
		HttpPost httpPost = null;
		
		httpClient = new DefaultHttpClient();
		httpPost = new HttpPost(ConstantUtils.IMG_UPLOAD_URL+"?type="+bType+"&bid="+bId);
		MultipartEntity reqEntity = new MultipartEntity();
		File[] f1 = new File[file.length];
		
		try {
			for (int i = 0; i < file.length; i++) {		
				f1[i] = new File(file[i].getParent() + "/" + fileName[i]);
				file[i].renameTo(f1[i]);				
				FileBody fileBody = new FileBody(f1[i]);
				reqEntity.addPart("file"+i, fileBody);
			}
			
			httpPost.setEntity(reqEntity);
			
			httpResponse = httpClient.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
				res = EntityUtils.toString(httpResponse.getEntity());
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			for (File temp : f1) {
				temp.delete();
			}
			httpPost.abort();
			httpClient.getConnectionManager().shutdown();
		}	
		
		return res;
	}
	
  public static String convertStreamToString(InputStream is) {      
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));      
    StringBuilder sb = new StringBuilder();      
   
    String line = null;      
    try {      
      while ((line = reader.readLine()) != null) {  
        sb.append(line + "\n");      
      }      
    } 
    catch (IOException e) {      
      e.printStackTrace();      
    } 
    finally {      
      try {      
      	is.close();      
      } 
      catch (IOException e) {      
        e.printStackTrace();      
      }      
    } 
    
    return sb.toString();      
  }	
}
