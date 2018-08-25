package com.xiaohu.demo.controller.jersey;

import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaohu.demo.utils.http.JerseyUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/jersey")
public class JerseyController {
	
	@SuppressWarnings("deprecation")
	@GetMapping("/jerseyParam")
	@ResponseBody
	public List<Map<String,Object>> jerseyParam(@PathParam("regNo") String regNo){
		String url="http://36.7.136.165:20011/jersey/rest/dossier/syncRisLisItemEmrData/ris?flag=2";
		String json = JerseyUtil.post(url, regNo);
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = JSONArray.toList(JSONArray.fromObject(json),Map.class);
		return list;
	}
}
