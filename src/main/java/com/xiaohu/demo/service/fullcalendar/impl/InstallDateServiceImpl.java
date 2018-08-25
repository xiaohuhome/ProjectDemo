package com.xiaohu.demo.service.fullcalendar.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaohu.demo.dao.fullcalendar.InstallDateDao;
import com.xiaohu.demo.entity.fullcalendar.Event;
import com.xiaohu.demo.entity.fullcalendar.InstallDate;
import com.xiaohu.demo.repository.fullcalendar.EventRepository;
import com.xiaohu.demo.repository.fullcalendar.InstallDateRepository;
import com.xiaohu.demo.service.fullcalendar.InstallDateService;
import com.xiaohu.demo.utils.list.ListUtils;

@Service("installDateService")
public class InstallDateServiceImpl implements InstallDateService {
	
	@Resource
	private InstallDateRepository installDateRepository;
	
	@Resource
	private InstallDateDao installDateDao;
	
	@Resource
	private EventRepository eventRepository;
	
	@Override
	public void saveOrUpdate(InstallDate installDate) {
		installDateRepository.save(installDate);
	}

	@Override
	public Map<String, Object> initInstallDate(String userId) {
		List<Map<String,Object>> list = installDateDao.initInstallDate(userId);
		if(ListUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveOrUpdate(Event event) {
		eventRepository.save(event);
	}

}
