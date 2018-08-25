package com.xiaohu.demo.controller.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaohu.demo.dao.common.CommonDao;
import com.xiaohu.demo.dao.hospital.HospitalDao;
import com.xiaohu.demo.dao.user.UserDao;
import com.xiaohu.demo.entity.user.BedNo;
import com.xiaohu.demo.repository.user.UserRepository;
import com.xiaohu.demo.service.hospital.HospitalService;
import com.xiaohu.demo.service.user.UserService;
import com.xiaohu.demo.utils.uuid.UUIDUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private HospitalService hospitalService;
	
	@Resource
	private UserService userService;
	
	//这里指定是条状的jsp界面
	@RequestMapping(value = "/index")
	public String index(Model model) {
		String uuid = UUIDUtil.generateUUID();
		List<Map<String,Object>> hospitalList = hospitalService.getAllHospitalList();
 		model.addAttribute("uuid", uuid);
 		model.addAttribute("hospitalList",hospitalList);
		return "/jsp/user/index";
	}
	
	@RequestMapping(value = "/date")
	public String date() {
		return "/jsp/fullcalendar/fullcalendar";
	}
	public String getFlag(String bedNo) {
		String str=bedNo;
        String regEx="\\D";
        Pattern p=Pattern.compile(regEx);
        Matcher m=p.matcher(str);
        String result=m.replaceAll("").trim();
        Character ch=result.charAt(0);
        int index=str.indexOf(ch);
        String diqu=str.substring(0,index);
        return diqu;
	}
	
	public String getNum(String bedNo) {
		Pattern pattern = Pattern.compile("[^0-9]");
		Matcher matcher = pattern.matcher(bedNo);
		String all = matcher.replaceAll("");
		return all;
	}
	
	@RequestMapping("/bedNo")
	public void bedNo() {
		List<Map<String,Object>> list = userService.getAllBedNo();
		ArrayList<BedNo> bedNoList = new ArrayList<>();
		BedNo bedNo = null;
		String bedNoStr = "";
		String flag = "";
		String num = "";
		for(Map<String,Object> bn:list) {
			bedNoStr = (String)bn.get("bed_no");
			bedNoStr = bedNoStr.replace("床", "");
			//数字
			if(bedNoStr.matches("[0-9]*")) {
				flag = "0";
				num = bedNoStr;
			}
			//字母
			else if(bedNoStr.matches("^[a-zA-Z][a-zA-Z0-9_]*$")) {
				flag = getFlag(bedNoStr);
				num = getNum(bedNoStr);
			}
			//汉字
			else if(bedNoStr.matches("^[\\u4e00-\\u9fa5][0-9]*$")) {
				flag = getFlag(bedNoStr);
				num = getNum(bedNoStr);
			}
			//字母加汉字
			else if(bedNoStr.matches("^[a-zA-Z][\\u4e00-\\u9fa5]+[0-9]*$")) {
				flag = getFlag(bedNoStr);
				num = getNum(bedNoStr);
			}else {
				flag = "";
				num = "99999";
			}
			
			bedNo = new BedNo();
			bedNo.setId((String)bn.get("id"));
			bedNo.setFlag(flag);
			bedNo.setNum(num);
			bedNoList.add(bedNo);
			if(num.matches(".*[\\u4e00-\\u9fa5]+.*") || num.matches(".*[a-zA-Z]+.*")) {
				System.out.println(bedNo.getId());
			}
		}
		
		System.out.println(bedNoList);
		
		userService.batchSaveBedNo(bedNoList);
	}
	
	
	
	 
	 
	@RequestMapping(value = "/json")
	@ResponseBody
	public Map<String, Object> mytest() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Ryan");
		map.put("age", "3");
		map.put("sex", "man");
		return map;
	}
}
