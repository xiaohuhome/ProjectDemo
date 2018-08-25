package com.xiaohu.demo.utils.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xiaohu.demo.job.MsgJob;

@Component
public class SpringUtils implements ApplicationContextAware {
	
	private static ApplicationContext applicationContext;
	
	private static Log logger = LogFactory.getLog(SpringUtils.class);
	
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        
    	if(SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }

    	logger.info("ApplicationContext配置成功,在普通类可以通过调用SpringUtils.getAppContext()获取applicationContext对象,applicationContext="+SpringUtils.applicationContext);
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }
    
    public static HttpServletRequest getRequest() {
    	ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		return request;
    }
    
    public static HttpSession getSession() {
		return getRequest().getSession();
    }
}
