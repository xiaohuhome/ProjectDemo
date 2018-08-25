package com.xiaohu.demo.service.upload;

import java.util.*;

import com.xiaohu.demo.entity.upload.UploadAttachment;

public interface UploadService {
	public void uploadFile(ArrayList<UploadAttachment> uaList);
	public List<Map<String,Object>> getFileDataList(String uuid,String type);
}
