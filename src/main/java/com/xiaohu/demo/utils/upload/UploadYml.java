package com.xiaohu.demo.utils.upload;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component 
@ConfigurationProperties(prefix = "upload")
public class UploadYml {
	private String basePath;
	private Map<String,String> contentTypeMap = new HashMap<String, String>();
	private String fileUrlForProgram;
	
	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}
	public Map<String, String> getContentTypeMap() {
		return contentTypeMap;
	}
	public void setContentTypeMap(Map<String, String> contentTypeMap) {
		this.contentTypeMap = contentTypeMap;
	}
	public String getFileUrlForProgram() {
		return fileUrlForProgram;
	}
	public void setFileUrlForProgram(String fileUrlForProgram) {
		this.fileUrlForProgram = fileUrlForProgram;
	}
	
	public String getFileType(String fileName) {
		String type = null;
		
		try {
			type = fileName.substring(fileName.lastIndexOf('.') + 1);
		} catch (Exception e) {
		}
		
		return type;
	}
	
	public String getContentType(String url) {
		return contentTypeMap.get(getFileType(url).toUpperCase());
	}	
}
