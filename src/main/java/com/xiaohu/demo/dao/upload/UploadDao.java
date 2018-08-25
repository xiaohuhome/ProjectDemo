package com.xiaohu.demo.dao.upload;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xiaohu.demo.dao.common.CommonDao;

@Component
public class UploadDao {
	
	@Resource
	private CommonDao commonDao;
	
	public List<Map<String,Object>> getFileDataList(String uuid,String type){
		StringBuilder sql = new StringBuilder();
		sql.append(" select u.* ")
		   .append(" from t_upload_attachment u ")
		   .append(" where u.uuid = ? and type = ? ");
		return commonDao.queryForList(sql.toString(), uuid,type);
	}
}
