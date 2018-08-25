package com.xiaohu.demo.controller.upload;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xiaohu.demo.entity.upload.UploadAttachment;
import com.xiaohu.demo.repository.upload.UploadRepository;
import com.xiaohu.demo.service.upload.UploadService;
import com.xiaohu.demo.utils.date.DateUtils;
import com.xiaohu.demo.utils.properties.ConstantUtils;
import com.xiaohu.demo.utils.upload.UploadYml;
import com.xiaohu.demo.utils.uuid.UUIDUtil;

/** 
* @author zhouhu
* @version 创建时间：2018年5月15日 下午5:29:03 
* 注意：上传文件必须添加@MultipartConfig()可以设置上传文件的大小
*/
@Controller
@RequestMapping("/mupload")
public class MUploadController{
	
	@Resource
	private UploadService uploadService;
	
	@Resource
	private UploadYml uploadInfo;
	
	@Resource
	private UploadRepository uploadRepository;
	
	@RequestMapping(value="/msave",method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String,Object> msave(HttpServletRequest request,@RequestParam(value="upload") MultipartFile[] multfiles,String[] imageData) throws Exception {
	  	Map<String,Object> fileMap = new HashMap<>();
		String uuid = request.getParameter("uuid");
	  	String type = request.getParameter("type");
	  	Map<String,Object> result = new HashMap<>();
	  	UploadAttachment ua = new UploadAttachment();
  		ua.setUuid(uuid);
  		ua.setType(type);
  		
	  	//本地图片上传
	  	if (multfiles!=null && multfiles.length > 0) {
	  		String[] fileName = getFileNames(multfiles);
	  		String[] fileArr = getFiles(multfiles, request); 
	  		fileMap.put("fileName", fileName);
	  		fileMap.put("fileArr", fileArr);
	  		fileMap.put("ua", ua);
	  		result = save(fileMap);
	  	}else {
	  		result.put("msg", "请选择上传的文件");
	  	}
	  	//拍照图片上传
	  	if(imageData!=null && imageData.length>0) {
	  		String[] fileArr = getFileUrl(imageData);
	  		fileMap.put("fileName", fileArr);
	  		fileMap.put("fileArr", fileArr);
	  		fileMap.put("ua", ua);
	  		result = save(fileMap);
	  	}else {
	  		result.put("msg", "请拍一张照片");
	  	}
	  	
	  	Map<String,Object> map = new HashMap<String, Object>();
	  	ArrayList<UploadAttachment> lstAttachement = (ArrayList<UploadAttachment>)result.get("lstAttachement");
		if(lstAttachement!=null && lstAttachement.size()>0){
			for (UploadAttachment uploadAttachment : lstAttachement) {
				uploadAttachment.setUrl(ConstantUtils.IMG_SHOW_URL+uploadAttachment.getUrl());
			}
			map.put("success", true);
			map.put("msg", "0");
			map.put("data", lstAttachement);
		}
		else{
			map.put("success", false);
			map.put("msg", result.get("msg"));
		}
			
		Map<String,Object> mResult = new HashMap<String, Object>();
		mResult.put("mResult", map);
	  	
	    return mResult;
	} 
	
	public Map<String,Object> save(Map<String,Object> map) {
		Map<String,Object> result = new HashMap<>();
		UploadAttachment ua = (UploadAttachment)map.get("ua");
		if (ua == null) {
			ua = new UploadAttachment();
		}

		if (StringUtils.isEmpty(ua.getId())) {
			ua.setId(UUIDUtil.generateUUID());
		}

		String uuid = ua.getUuid(); 
		String type = ua.getType();
		
		if (type == null || "".equals(type.trim()) || "null".equals(type.trim())){
			result.put("msg","type不能为空");
			return result;
		}
		
		if (uuid == null || "".equals(uuid.trim()) || "null".equals(uuid.trim())){
			result.put("msg","uuid不能为空");
			return result;
		}
		
		String[] fileNameArray = (String[])map.get("fileName");
		String[] fileArr = (String[])map.get("fileArr");
		
		if (fileNameArray==null || fileNameArray.length==0 || fileArr==null || fileArr.length==0) {
			result.put("msg","请选择上传文件");
			return result; 
		} 
		String exName = "";
		for (int i = 0; i < fileNameArray.length; i++) {
			exName = fileNameArray[i].substring(fileNameArray[i].lastIndexOf('.') + 1).toUpperCase();
			
			if (StringUtils.isEmpty(uploadInfo.getContentTypeMap().get(exName))) {
				result.put("msg","文件类型错误");
				return result; 
			}	
		}
		
		//将文件信息保存到数据库
		
		if (fileArr.length > 0) {
			ArrayList<UploadAttachment> lstAttachement = new ArrayList<UploadAttachment>();
			UploadAttachment uploadAttachment = null;
			for (int i = 0; i < fileArr.length; i++) {
				uploadAttachment = new UploadAttachment();
				uploadAttachment.setId(UUIDUtil.generateUUID());
				uploadAttachment.setUuid(uuid);
				uploadAttachment.setType(type);
				exName = fileArr[i].substring(fileArr[i].lastIndexOf('.') + 1).toUpperCase();
				uploadAttachment.setFileType(uploadInfo.getContentTypeMap().get(exName));
				uploadAttachment.setFileName("");
				uploadAttachment.setUrl(fileArr[i].substring(fileArr[i].indexOf("/file")+5));
				uploadAttachment.setAddDate(DateUtils.yyyyMMdd(new Date()));
				uploadAttachment.setAddTime(DateUtils.yyyyMMddHHmmss(new Date()));
				uploadAttachment.setDisabled("0");
				lstAttachement.add(uploadAttachment);
			}
			if(lstAttachement!=null && lstAttachement.size()>0) {
				uploadService.uploadFile(lstAttachement);
				result.put("lstAttachement", lstAttachement);
			}
		}
		return result;
	}
	//获取本地上传的文件名 
	private String[] getFileNames(MultipartFile[] multfiles) {
	  	String[] names = new String[multfiles.length];
	  	
	  	for (int i = 0; i < multfiles.length; i++) {
	  		names[i] = multfiles[i].getOriginalFilename();
	  	}
	  	
	  	return names;
	}
	  
	  /** 
	   * getFiles 获取本地上传的文件路径 
	   *  
	   * @param multfile 原文件类型 
	   * @return File 
	   * @throws  
	   */  
	  private String[] getFiles(MultipartFile[] multfiles, HttpServletRequest request) {  
	  	String[] files = new String[multfiles.length];
	  	String uploadPath=ConstantUtils.IMG_UPLOAD_URL + "/" + DateUtils.yyyyMM(new Date())+ "/" + DateUtils.yyyyMMdd1(new Date());
	  	File file = new File(uploadPath);
	  	if(!file.exists()) {
        	file.mkdirs();
        }
	  	String filePath = "";
	  	String path = "";
	  	for (int i = 0; i < multfiles.length; i++) {
		  	filePath=multfiles[i].getOriginalFilename();
		  	path=uploadPath + "/" + String.valueOf(System.currentTimeMillis()) + filePath.substring(filePath.lastIndexOf("."));
		  	files[i] = path;
		  	file = new File(path);
		  	try {
				multfiles[i].transferTo(file);
			}catch (IOException e) {
				e.printStackTrace();
			}  		
	  	}  	
	 
	    return files;  
	  }
	  
	  //加载所有附件
	  @RequestMapping("/fileList")
	  public String fileList(HttpServletRequest request,Model model) {
		  String uuid = request.getParameter("uuid");
		  String type = request.getParameter("type");
		  List<Map<String,Object>> fileDataList = uploadService.getFileDataList(uuid,type);
		  for (Map<String,Object> uploadAttachment : fileDataList) {
				uploadAttachment.put("url",ConstantUtils.IMG_SHOW_URL+uploadAttachment.get("url"));
			}
		  model.addAttribute("uuid", uuid);
		  model.addAttribute("fileDataList", fileDataList);
		  return "/jsp/upload/uploadData";
	  }
	  //删除附件
	  @PostMapping("/deleteById")
	  @ResponseBody
	  public Map<String,Object> deleteById(@PathParam("id") String id) {
		  Map<String,Object> map = new HashMap<>();
		  uploadRepository.deleteById(id);
		  map.put("msg", "0");
		  return map;
	  }
	  
	  //拍照上传解码
	  public static byte[] decodeBase64(String input) throws Exception{
		  	Class clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		  	Method mainMethod = clazz.getMethod("decode", String.class);
		  	mainMethod.setAccessible(true);
		  	Object retObj = mainMethod.invoke(null, input);
		  	return (byte[]) retObj;
	  }
	  
	  //拍照上传并返回路径
	  public static String[] getFileUrl(String imageData[]) throws Exception {
		  	String fileUrl[] = new String[imageData.length];
		  	//上传的文件夹
			String uploadPath=ConstantUtils.IMG_UPLOAD_URL + "/" + DateUtils.yyyyMM(new Date())+ "/" + DateUtils.yyyyMMdd1(new Date());
			
			File file = new File(uploadPath);
		  	if(!file.exists()) {
	        	file.mkdirs();
	        }
		  	byte[] k;
		  	InputStream is;
		  	FileOutputStream fos;
		  	String path;
		  	for(int i =0;i<imageData.length;i++) {
		  	//注意点：实际的图片数据是从 data:image/jpeg;base64后开始的 ,在前台我们已将该数据格式掉了
				k = decodeBase64(imageData[i]);
				is = new ByteArrayInputStream(k);
				
				//上传文件路径
				path=uploadPath + "/" + String.valueOf(System.currentTimeMillis()) + ".jpg";
				fileUrl[i] = path;
				file = new File(path);
				//创建文件
				if (!file.exists()){
					file.createNewFile();
				}
				
				fos = new FileOutputStream(file);
				//写入文件
				fos.write(k);
				fos.flush();
				fos.close();
				fos = null;
		  	}
		  	return fileUrl;
	  }
	  
	  
	  //录像录音上传
	  //未实现
	  @RequestMapping("/uploadAudio")
	  public void uploadAudio(HttpServletRequest request,MultipartFile userfile) throws IOException{
		  	String uploadPath=ConstantUtils.IMG_UPLOAD_URL + "/" + DateUtils.yyyyMM(new Date())+ "/" + DateUtils.yyyyMMdd1(new Date());
		  	File file = new File(uploadPath);
		  	if(!file.exists()) {
	        	file.mkdirs();
	        }
		  	 //获取上再是原始文件名
		    String fileName =  userfile.getOriginalFilename();
		    String path=uploadPath + "/" + String.valueOf(System.currentTimeMillis()) + fileName.substring(fileName.lastIndexOf("."));
		    System.out.println(fileName);
		    file = new File(path);
		    
		    try {
		    	userfile.transferTo(file);
			}catch (IOException e) {
				e.printStackTrace();
			}  		
		    
	  }
}
