package com.xiaohu.demo.utils.xml;  
  
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
  
/**
 * <p>标题: PeDescDto.java</p>
 * <p>业务描述:协和预约平台，解析xml用的标签类</p>
 * <p>公司:东华软件股份公司</p>
 * <p>版权:dhcc2015</p>
 * @author 宗宁
 * @date 2015-01-10
 * @version V1.0 
 */
@Target(ElementType.FIELD)  
@Retention(RetentionPolicy.RUNTIME)  
@Documented  
public @interface XmlToBeanInterface {  
	public static String TYPE_IGNORE = "TYPE_IGNORE"; //不自动解析和注入的属性
	public static String TYPE_LIST   = "TYPE_LIST"; //此属性对应一个List列表
	public static String TYPE_BEAN   = "TYPE_BEAN"; //此属性对应一个Bean对象
	
    String type();   //类型 
    String label();  //label值
} 