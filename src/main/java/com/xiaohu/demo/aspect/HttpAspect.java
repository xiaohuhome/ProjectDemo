package com.xiaohu.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class HttpAspect {
    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
    
    //不进行登录拦截的请求
    private static final String requestUrl = "|/login/userLogin|";
    
    @Pointcut("execution(public * com.xiaohu.demo.controller..*(..))  and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void log(){}
    
    @Around("log()")
    public Object doLogin(ProceedingJoinPoint pjp) throws Throwable {
    	System.out.println("LoginInterceptor");
    	ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if(requestUrl.contains("|"+request.getServletPath()+"|")) {
        	return pjp.proceed();
        }else {
        	@SuppressWarnings("unchecked")
			Map<String,Object> user = (Map<String, Object>) request.getSession().getAttribute("User");
            if(user == null) {
            	return "redirect:/login.jsp";
            }
        }
    	return pjp.proceed();
    }
    
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //url
        logger.info("请求的URL：{}",request.getRequestURL()+","+request.getContextPath()+","+request.getServletPath()+","+request.getRequestURI());
        //method
        logger.info("请求方式：{}",request.getMethod());
        //ip
        logger.info("客户端IP：{}",request.getRemoteAddr()+","+request.getLocalAddr()+","+request.getLocalPort());
        //类方法
        logger.info("请求的类方法：{}",joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        //参数
        logger.info("请求的参数：{}",joinPoint.getArgs());
        
    }
    
    @After("log()")
    public void doAfter(){
    	ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    	HttpServletResponse response = attributes.getResponse();
    	logger.info("请求的状态：{}",response.getStatus());
    }

    @AfterReturning(returning = "object",pointcut = "log()")
    public void doAfterReturn(Object object){
    	if(object != null) {
    		logger.info("RequestResult：{}",object.toString());
    	}
    }
}
