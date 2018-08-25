package com.xiaohu.demo.service.upload.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaohu.demo.dao.common.CommonDao;
import com.xiaohu.demo.dao.upload.UploadDao;
import com.xiaohu.demo.entity.upload.UploadAttachment;
import com.xiaohu.demo.service.upload.UploadService;

@Service("uploadService")
public class UploadServiceImpl implements UploadService{
	
	@Resource
	private CommonDao commonDao;
	
	@Resource
	private UploadDao uploadDao;

	@Override
	public void uploadFile(ArrayList<UploadAttachment> uaList) {
		commonDao.batchSaveOrUpdate(uaList, "t_upload_attachment");
	}
	
	@Override
	public List<Map<String,Object>> getFileDataList(String uuid,String type){
		return uploadDao.getFileDataList(uuid,type);
	}
	
}
