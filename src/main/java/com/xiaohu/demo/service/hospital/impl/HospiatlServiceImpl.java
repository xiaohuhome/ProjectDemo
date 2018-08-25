package com.xiaohu.demo.service.hospital.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaohu.demo.dao.hospital.HospitalDao;
import com.xiaohu.demo.service.hospital.HospitalService;

@Service("hospitalService")
public class HospiatlServiceImpl implements HospitalService{
	
	@Resource
	private HospitalDao hospitalDao;
	
	@Override
	public List<Map<String, Object>> getAllHospitalList() {
		return hospitalDao.getAllHospitalList();
	}

}
