package com.xiaohu.demo.controller.login;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xiaohu.demo.entity.user.User;
import com.xiaohu.demo.service.user.UserService;
import com.xiaohu.demo.utils.md5.DesEncoder;
import com.xiaohu.demo.utils.result.RequestResult;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Resource
	private UserService userService;
	
	@PostMapping("/userLogin")
	@ResponseBody
	public RequestResult userLogin(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username);
		System.out.println(password);
		
		Map<String,Object> user = userService.getUser(username);
		
		String msg = "0";
		if(user == null) {
			msg = "1";//用户不存在
		}else {
			if(DesEncoder.isPasswordValid((String)user.get("password"), password)) {
				msg = "0";//允许登录
				HttpSession session = request.getSession();
				session.setAttribute("User", user);
				session.setAttribute("userId", user.get("id"));
				session.setMaxInactiveInterval(60*30);
			}else {
				msg = "2";//密码错误
			}
		}
		
		return new RequestResult(msg);
	}
}
