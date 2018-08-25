package com.xiaohu.demo.utils.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConstantUtils {
private static Properties p = new Properties();
	
	static {
		InputStream inputStream = ConstantUtils.class.getClassLoader().getResourceAsStream("constant.properties");   
		
		try {   
			p.load(inputStream);   
		} catch (IOException e1) {   
			e1.printStackTrace();  
		}   
	}
	
	public static String IMG_UPLOAD_URL = p.getProperty("img_upload_url");
	public static String IMG_SHOW_URL = p.getProperty("img_show_url");
	public static String EXEC_JOB_FLAG = p.getProperty("exec_job_flag");
}
