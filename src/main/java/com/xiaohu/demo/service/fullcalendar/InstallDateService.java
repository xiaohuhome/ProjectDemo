package com.xiaohu.demo.service.fullcalendar;

import java.util.Map;

import com.xiaohu.demo.entity.fullcalendar.Event;
import com.xiaohu.demo.entity.fullcalendar.InstallDate;

public interface InstallDateService {
	public void saveOrUpdate(InstallDate installDate);
	public Map<String,Object> initInstallDate(String userId);
	public void saveOrUpdate(Event event);
}
