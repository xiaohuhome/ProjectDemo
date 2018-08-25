package com.xiaohu.demo.dao.fullcalendar;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xiaohu.demo.dao.common.CommonDao;

@Component
public class InstallDateDao{
	@Resource
	private CommonDao commonDao;
	public List<Map<String,Object>> initInstallDate(String userId){
		String sql ="select * from t_install_date where user_id = ? ";
		return commonDao.queryForList(sql, userId);
	}
}
