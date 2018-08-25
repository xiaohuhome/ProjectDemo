package com.xiaohu.demo.controller.fullcalendar;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xiaohu.demo.entity.fullcalendar.Event;
import com.xiaohu.demo.entity.fullcalendar.InstallDate;
import com.xiaohu.demo.service.fullcalendar.InstallDateService;
import com.xiaohu.demo.utils.date.DateUtils;
import com.xiaohu.demo.utils.result.RequestResult;
import com.xiaohu.demo.utils.spring.SpringUtils;
import com.xiaohu.demo.utils.uuid.UUIDUtil;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

@Controller
@RequestMapping("/fullcalendar")
public class FullcalendarController {
	
	@Resource
	private InstallDateService installDateService;
	
	@RequestMapping("/installOk")
	@ResponseBody
	public RequestResult installOk(InstallDate installDate) {
		HttpSession session = SpringUtils.getSession();
		String userId = (String)session.getAttribute("userId");
		installDate.setId(UUIDUtil.generateUUID());
		installDate.setUserId(userId);
		installDateService.saveOrUpdate(installDate);
		return new RequestResult("操作成功","200",null,true);
	}
	
	@RequestMapping("/initInstallDate")
	@ResponseBody
	public RequestResult initInstallDate() {
		HttpSession session = SpringUtils.getSession();
		String userId = (String)session.getAttribute("userId");
		Map<String,Object> result = installDateService.initInstallDate(userId);
		return new RequestResult("操作成功","200",result,true);
	}
	
	@RequestMapping("/buildEvent")
	@ResponseBody
	public RequestResult buildEvent(Event event) {
		HttpSession session = SpringUtils.getSession();
		String userId = (String)session.getAttribute("userId");
		
		event.setUserId(userId);
		event.setAddDate(DateUtils.yyyyMMdd(new Date()));
		event.setAddTime(DateUtils.yyyyMMddHHmmss(new Date()));
		
		installDateService.saveOrUpdate(event);
		return new RequestResult("操作成功","200",JSONArray.fromObject(event),true);
	}
	
	@RequestMapping("/buildSchedule")
	@ResponseBody
	public RequestResult buildSchedule(HttpServletRequest request) {
		HttpSession session = SpringUtils.getSession();
		String userId = (String)session.getAttribute("userId");
		Map<String,Object> result = new HashMap<>();
		
		result.put("title", request.getParameter("title"));
		result.put("start", request.getParameter("sdate")+" "+request.getParameter("stime"));
		result.put("end", request.getParameter("edate")+" "+request.getParameter("etime"));
		result.put("color", request.getParameter("color"));
		result.put("allDay", request.getParameter("allDay"));
		return new RequestResult("操作成功","200",JSONArray.fromObject(result),true);
	}
}
