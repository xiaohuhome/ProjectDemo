package com.xiaohu.demo.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xiaohu.demo.entity.user.BedNo;

public interface UserService {
	public Map<String,Object> getUser(String username);
	public List<Map<String,Object>> getAllBedNo();
	public void batchSaveBedNo(ArrayList<BedNo> list);
}
