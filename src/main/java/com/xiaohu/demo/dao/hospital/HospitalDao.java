package com.xiaohu.demo.dao.hospital;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xiaohu.demo.dao.common.CommonDao;

@Component
public class HospitalDao {
	
	@Resource
	private CommonDao commonDao;
	
	public List<Map<String,Object>> getAllHospitalList(){
		String sql = "select id,hos_name label from t_hospital where disable = '0' ";
		return commonDao.queryForList(sql);
	}
}
