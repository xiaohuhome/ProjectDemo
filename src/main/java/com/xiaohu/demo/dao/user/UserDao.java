package com.xiaohu.demo.dao.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.xiaohu.demo.dao.common.CommonDao;
import com.xiaohu.demo.utils.date.DateUtils;

@Component
public class UserDao {
	private static Log logger = LogFactory.getLog(UserDao.class);
	
	@Resource
	private CommonDao commonDao;
	
	public List<Map<String,Object>> getUserList(){
		String sql = "select * from t_user ";
		return commonDao.queryForList(sql);
	}
	
	public List<Map<String,Object>> getUser(String username){
		String sql = "select * from t_user where username = ? ";
		return commonDao.queryForList(sql, username);
	}
	
	
	public List<Map<String,Object>> getAllBedNo(){
		String sql = "select * from t_csm_bed_no ";
		return commonDao.queryForList(sql);
	}
	
	public void getDate() {
		logger.info("当前时刻："+ DateUtils.yyyyMMddHHmmss(new Date()));
	}
}
