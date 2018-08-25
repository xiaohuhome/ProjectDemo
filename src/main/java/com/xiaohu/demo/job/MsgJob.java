package com.xiaohu.demo.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.xiaohu.demo.dao.user.UserDao;
import com.xiaohu.demo.utils.date.DateUtils;
import com.xiaohu.demo.utils.spring.SpringUtils;

/**
 * <p>标题: MsgJob.java</p>
 */
public class MsgJob implements Job {

	private static Log logger = LogFactory.getLog(MsgJob.class);
	
	UserDao userDao = SpringUtils.getBean(UserDao.class);
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		userDao.getDate();
//		logger.info("当前时刻："+ DateUtils.yyyyMMddHHmmss(new Date()));
	}

}
