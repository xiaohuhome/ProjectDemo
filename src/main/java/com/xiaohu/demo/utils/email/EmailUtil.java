package com.xiaohu.demo.utils.email;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.xiaohu.demo.utils.date.DateUtils;

/**
 * 
 * <p>标题: EmailUtil.java</p>
 * <p>业务描述: </p>
 */
public class EmailUtil {
	public static void send(String code,String mail) throws EmailException{	
		String emailContent = "<div style='font-weight: bold; font-size: 16px; margin-bottom: 8px; margin-top: 20px;'>亲爱的用户"+
				  "<a href=mailto:"+ mail +" style='color:blue;'>"+mail+ "</a>"+"，您好</div>" +
	              "<div style='font-size: 13px;margin-bottom: 8px;'>欢迎注册健康乐账号，请输入以下验证码完成邮箱注册：</div>"  
	               +"<b>"+code+"</b>"+
	              "<div style='font-size: 13px; margin-top: 60px;'>健康乐软件有限公司</div>" +
	              "<div style='font-size: 13px; margin-top: 8px;'>" + DateUtils.getNowStrDate() + "</div>"+
	              "<div style='font-size: 13px; margin-top: 30px;'>本邮件由健康乐系统自动发出，请勿直接回复</div>";

		HtmlEmail email = new HtmlEmail();
		email.setCharset("UTF-8");
		email.setHostName("smtp.qq.com");		
		email.setAuthentication(mail, "mqihdeuniyzyigcd");
		email.addTo(mail);
		email.setFrom(mail);
		email.setSubject("健康乐注册");
		email.setHtmlMsg(emailContent);
		email.setSmtpPort(25);
		email.send();		
	}
	
	public static void sen(String code,String mail) throws EmailException{	
		String emailContent = "<div style='font-weight: bold; font-size: 16px; margin-bottom: 8px; margin-top: 20px;'>亲爱的用户"+
				  "<a href=mailto:"+ mail +" style='color:blue;'>"+mail+ "</a>"+"，您好</div>" +
	              "<div style='font-size: 13px;margin-bottom: 8px;'>请输入以下验证码完成密码修改：</div>"  
	               +"<b>"+code+"</b>"+
	              "<div style='font-size: 13px; margin-top: 60px;'>健康乐软件有限公司</div>" +
	              "<div style='font-size: 13px; margin-top: 8px;'>" + DateUtils.getNowStrDate() + "</div>"+
	              "<div style='font-size: 13px; margin-top: 30px;'>本邮件由健康乐系统自动发出，请勿直接回复</div>";

		HtmlEmail email = new HtmlEmail();
		email.setCharset("UTF-8");
		email.setHostName("smtp.qq.com");		
		email.setAuthentication(mail, "mqihdeuniyzyigcd");
		email.addTo(mail);
		email.setFrom(mail);
		email.setSubject("健康乐密码找回");
		email.setHtmlMsg(emailContent);
		email.setSmtpPort(25);
		email.send();		
	}
	
	//测试发送
	public static void main(String args[]){
		try {
			send("123456", "1275315823@qq.com");
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
