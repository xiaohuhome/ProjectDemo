package com.xiaohu.demo.utils.download;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import sun.net.www.protocol.ftp.FtpURLConnection;

public class FileUtils {
	public static final String fileUrl = "ftp://user:123.com@10.10.11.77";//ftp服务器连接
	public static final String downloadUrl = "D:/tomcat_7/webapps/jersey/upload";//文件下载保存路径
	/** 
     * 从网络Url中下载文件 
     * @param fileUrlStr 
     * @param fileName 
     * @param savePath
     * @param connType 图片服务器类型
     * @throws IOException 
     */  
    public static void downLoadFromUrl(String fileUrlStr,String fileName,String savePath,String connType) throws IOException{  
    	URL url = new URL(fileUrlStr);
    	URLConnection conn = null;
    	if("ftp".equals(connType)) {
    		conn = (FtpURLConnection)url.openConnection();
    	}else if("http".equals(connType)) {
    		conn = (HttpURLConnection)url.openConnection();
    	}
                //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
  
        //得到输入流  
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(savePath);  
        if(!saveDir.exists()){  
            saveDir.mkdirs();  
        }  
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);       
        fos.write(getData);   
        if(fos!=null){  
            fos.close();    
        }  
        if(inputStream!=null){  
            inputStream.close();  
        }  
    }  
  
  
  
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }
    
    public static void main(String[] args) throws IOException {
		String fileUrlStr = "http://p0.so.qhmsg.com/bdr/200_200_/t0155331b8066a81a3f.jpg";
		File file = new File(fileUrlStr);
		String fileName = file.getName();
		String savePath = "D://download";
		String connType = "http";
		downLoadFromUrl(fileUrlStr, fileName, savePath, connType);
	}
}
