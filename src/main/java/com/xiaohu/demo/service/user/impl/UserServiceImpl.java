package com.xiaohu.demo.service.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaohu.demo.dao.common.CommonDao;
import com.xiaohu.demo.dao.user.UserDao;
import com.xiaohu.demo.entity.user.BedNo;
import com.xiaohu.demo.service.user.UserService;
import com.xiaohu.demo.utils.list.ListUtils;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private CommonDao commonDao;
	
	public void batchSaveBedNo(ArrayList<BedNo> list){
		commonDao.batchSaveOrUpdate(list, "t_bed_no");
	}
	
	@Override
	public Map<String, Object> getUser(String username) {
		List<Map<String,Object>> userList = userDao.getUser(username);
		if(ListUtils.isNotEmpty(userList)) {
			return userList.get(0);
		}
		return null;
	}

	public List<Map<String,Object>> getAllBedNo(){
		return userDao.getAllBedNo();
	}
}
