package com.xiaohu.demo.utils.xml;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;


/**
* <p>标题: PeUtil.java</p>
* <p>业务描述:协和体检工具类</p>
* <p>公司:东华软件股份公司</p>
* <p>版权:dhcc2015</p>
* @author 宗宁
* @date 2015年1月4日
* @version V1.0
 */
public class XmlToBeanUtil {
	
	/**
	（解析返回数据的倒数第二层对象列表节点）
	 * 复制数据列表
	 * @param <T>
	 * @param list
	 * @param soapObj
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static <T> List<T> parseBeansToList(Class<T> classzz,String xmlData)
	{
		List<Map> beansList = pasreXMLToMapList(classzz,xmlData);
		
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < beansList.size(); i++)
		{
		 try {
				T targetObj = classzz.newInstance();
				//第i个bean
				Map beanSoap = beansList.get(i);
				copyProperty(targetObj, beanSoap);
				
				list.add(targetObj);
				
			} catch (Exception e) {
//				throw new DataBaseException(e.getMessage(),e);
			}  
		}
		return list;
	}
	
	/**
	（解析返回数据的倒数第二层对象列表节点）
	 * 复制数据列表
	 * @param <T>
	 * @param list
	 * @param soapObj
	 * @throws Exception 
	 */
	public static <T> List<T> parseBeansToList(Class<T> classzz,String firstLevelNodeName,String xmlData) throws Exception
	{
		List<Map<String, String>> beansList = pasreXMLToMapList(classzz,firstLevelNodeName,xmlData);
		
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < beansList.size(); i++)
		{
		 try {
				T targetObj = classzz.newInstance();
				//第i个bean
				Map<String, String> beanSoap = beansList.get(i);
				copyProperty(targetObj, beanSoap);
				
				list.add(targetObj);
				
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw e;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw e;
			}  catch (Exception e) {
				e.printStackTrace();
				throw e;
			}  
		}
		return list;
	}
	
	/**
	 *  （解析返回数据的倒数第二层对象列表节点）
	 * 复制数据列表
	 * @param <T>
	 * @param list
	 * @param soapObj
	 */
	@SuppressWarnings({"rawtypes", "unused" })
	private static <T> List<T> parseBeansToList(Class<T> classzz,List<Map> beansList)
	{
		List<T> list = new ArrayList<T>();
		
		for (int i = 0; i < beansList.size(); i++)
		{
		 try {
			 
				T targetObj = classzz.newInstance();
				//第i个bean
				Map beanSoap = beansList.get(i);
				copyProperty(targetObj, beanSoap);
				
				list.add(targetObj);
				
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}  
		}
		return list;
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<Map> pasreXMLToMapList(Class classzz,String xml){
		List<Map> list= new ArrayList();
		Document doc;
		try {
			
			 doc = DocumentHelper.parseText(xml);
			 Element root = doc.getRootElement();// 指向根节点   
		     // normal解析
	         Element eCode = root.element("ResultCode"); 
	         String code = eCode.getText();
	         if(!"0".equals(code)){
	        	 return list;
	         }
	         
	         String nodeName = classzz.getSimpleName();
	         Element listEl = root.element("ResultList");   
	         List listChild = listEl.elements(nodeName);// 所有的Item节点   
             for (int i = 0; i < listChild.size(); i++) {   
                 Element elChild = (Element) listChild.get(i); 
                 Map map = new HashMap();
                 copyDataToMap(map,elChild);
                 list.add(map);
             }  

		} catch (Exception e) {
			e.printStackTrace();
//			throw new DataBaseException(e.getMessage(),e);
		}
		return list ;
	}
	
	
	/**
	 * 此方法需要对参数进行强制类型转化，可能导致不必要的类型转化错误，不对外公开
	 *（解析返回数据的倒数第二层对象列表节点）
	 * 复制数据列表
	 * @param <T>
	 * @param list
	 * @param soapObj
	 * @throws Exception 
	 */
	@SuppressWarnings({"rawtypes", "unchecked"})
	private static <T> List<T> parseBeansToList2(Class classzz,String xmlData) throws Exception
	{
		List<Map> beansList = pasrePropertyXMLToMapList2(classzz,xmlData);
		
		List<T> list = new ArrayList<T>();
		for (int i = 0; i < beansList.size(); i++)
		{
		 try {
				T targetObj = (T)classzz.newInstance();
				//第i个bean
				Map beanSoap = beansList.get(i);
				copyProperty(targetObj, beanSoap);
				
				list.add(targetObj);
				
			} catch (InstantiationException e) {
				e.printStackTrace();
				throw e;
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw e;
			}  catch (Exception e) {
				e.printStackTrace();
				throw e;
			}  
		}
		return list;
	}
	/**
	 * 解析多级xml时使用
	 * @param classzz
	 * @param propertyName
	 * @param innerXml
	 * @return
	 * @throws BaseException
	 * @throws DocumentException
	 */
	@SuppressWarnings("rawtypes")
	private static List<Map> pasrePropertyXMLToMapList2(Class classzz,String innerXml){
		@SuppressWarnings("unchecked")
		List<Map> list= new ArrayList();
		Document doc;
		try {
			 doc = DocumentHelper.parseText(innerXml);
			 Element root = doc.getRootElement();// 指向根节点   
		     // normal解析   
	         String nodeName = classzz.getSimpleName();
	         List<?> listChild = root.elements(nodeName);// 所有的Item节点   
             for (int i = 0; i < listChild.size(); i++) {   
                 Element elChild = (Element) listChild.get(i); 
                 Map<String, String> map = new HashMap<String, String>();
                 copyDataToMap(map,elChild);
                 list.add(map);
             }  

		} catch (Exception e) {
//			throw new Exception(e.getMessage(),e);
		}
		return list ;
	}
	
	
	/**
	 * 解析多级xml时使用
	 * @param classzz
	 * @param propertyName
	 * @param innerXml
	 * @return
	 * @throws BaseException
	 * @throws DocumentException
	 */
	@SuppressWarnings("rawtypes")
	private static Map pasrePropertyXMLToMap(Class classzz,String innerXml){
		Document doc;
		Map<String, String> map = new HashMap<String, String>();
		
		if (innerXml == null || "".equals(innerXml)) {
			return map;
		}
		
		try {
			 doc = DocumentHelper.parseText(innerXml);
			 Element root = doc.getRootElement();// 指向根节点   
		     // normal解析   
	         //String nodeName = classzz.getSimpleName();
             copyDataToMap(map,root);

		} catch (Exception e) {
//			throw new DataBaseException(e.getMessage(),e);
		}
		return map ;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static List<Map<String, String>> pasreXMLToMapList(Class classzz,String firstLevelNodeName,String xml){
		List<Map<String, String>> list= new ArrayList<Map<String, String>>();
		Document doc;
		try {
			 doc = DocumentHelper.parseText(xml);
			 Element root = doc.getRootElement();// 指向根节点   
		     // normal解析   
	         Element eCode = root.element("ResultCode"); 
	         String code = eCode.getText();
	         
	         if(!"0".equals(code)){
//	        	 throw new DataBaseException(desc);
	         }
	         
	         String nodeName = null;
	         if(firstLevelNodeName == null || "".equals(firstLevelNodeName)){
	        	 nodeName = classzz.getSimpleName();
	         }else{
	        	 nodeName = firstLevelNodeName;
	         }
	         Element listEl = root.element("ResultList");   
	         List listChild = listEl.elements(nodeName);// 所有的Item节点   
             for (int i = 0; i < listChild.size(); i++) {   
                 Element elChild = (Element) listChild.get(i); 
                 Map map = new HashMap();
                 copyDataToMap(map,elChild);
                 list.add(map);
             }  

		} catch (Exception e) {
//			throw new DataBaseException(e.getMessage(), e);
		}
		return list ;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> T parseToBean(Class classzz,String xml) throws DocumentException,Exception{
		T targetObj = (T) classzz.newInstance();
		Map map = pasrePropertyXMLToMap(classzz,xml);
		//第i个bean
		copyProperty(targetObj,map);
		return targetObj;
	}
	
	/**
	 * （解析返回数据的倒数第一层对象节点）
	 * 把一个单层的soap对象属性复制到目标对象中，两个对象的属性必须一一对应
	 * @param targetObj 目标对象
	 * @param soapObj   操作对象
	 */
	@SuppressWarnings("rawtypes")
	private static void copyProperty(Object targetObj, Map map) {

		Field[] fields = targetObj.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			//property
			String fieldName = field.getName() ;
			Object fieldValue = map.get(fieldName);
			
			XmlToBeanInterface desc = field.getAnnotation(XmlToBeanInterface.class) ;
			if(desc!=null && desc.type().equals(XmlToBeanInterface.TYPE_IGNORE)){
				continue;
			}
			if("serialVersionUID".equals(fieldName)){
				continue;
			}
			//解析到List集合
			// if(desc!=null && desc.type().equals(Desc.TYPE_LIST)){
			if("java.util.List".equals(field.getType().getCanonicalName())){
				//范型参数类型
				Class classParam = getTypeParam0(field);
				try {
					if(fieldValue!=null){// fieldValue 为一段xmlNode
						fieldValue = XmlToBeanUtil.parseBeansToList2(classParam,fieldValue.toString());
					}else{
						fieldValue = new ArrayList();
					}
				} catch (Exception e) {
//					throw new DataBaseException(e.getMessage(),e);
				}
			}
			if(desc!=null && XmlToBeanInterface.TYPE_BEAN.equals(desc.type())){
				//范型参数类型
				Class classParam = field.getType();
				try {
					if(fieldValue!=null){// fieldValue 为一段xmlNode
						fieldValue = XmlToBeanUtil.parseToBean(classParam,fieldValue.toString());
					}
					
				} catch (Exception e) {
//					throw new DataBaseException(e.getMessage(),e);
				}
			}
			try {
				field.set(targetObj,fieldValue);
			} catch(Exception e){
//				throw new DataBaseException(e.getMessage(),e);
			}
		}

	}
	

	/**
	 * 获取集合属性的第一个范型参数类型
	 * @param listField
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static Class getTypeParam0(Field listField){
		 Class classRet = null ;
		 Type genericFieldType = listField.getGenericType();
	     if(genericFieldType instanceof ParameterizedType){
	    	 ParameterizedType aType = (ParameterizedType)genericFieldType;
	    	 Type[] fieldArgTypes = aType.getActualTypeArguments();
 		     for(Type fieldArgType:fieldArgTypes){
 		    	classRet = (Class)fieldArgType;
 		    	break ;//只获取第一个，后面的不管了
 		     }
	     }

		return classRet;
	}
	/**
	 * 复制人员信息到map
	 * @param map
	 * @param elChild
	 */
	private static void copyDataToMap(Map<String, String> map, Element elChild) {
		 List<?> properties = elChild.elements();  
         for (int i = 0; i < properties.size(); i++) {
             Element p = (Element) properties.get(i); 
             String key = p.getName();
             String v = null ;
            
             //if(p.selectNodes("//*").size()>0){//dom4j判断存在子节点
             if(p.elements().size()>0){
            	 v = p.asXML();
             }else{
            	 v = p.getText();
             }
             map.put(key, v);
         }  
	}

	/**
	 * @param dataBean
	 * @return
	 */
	
	public static String buildRequestXmlByBean(Object dataBean) {
		StringBuilder sb = new StringBuilder() ;
		sb.append("<Request>");
		
		Field[] fields = dataBean.getClass().getDeclaredFields();
		for (Field field : fields) {
			
			field.setAccessible(true);
			//property
			try {
				
				String key = field.getName() ;
				Object v = field.get(dataBean);
				if("serialVersionUID".equals(key)){
					continue;
				}
				if(v==null){
					continue;//查获取非空属性的参数
				}
				
				sb.append("<").append(key).append(">");
				sb.append(v);
				sb.append("</").append(key).append(">");
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		sb.append("</Request>");
		return sb.toString();		
		
	}

	/**
	 * 
		* 方法名:          buildRequestXmlByMap
		* 方法功能描述:     将map里的各节点内容拼成xml
		* @param:         mapParam
		* @param:         topNodeName 想要额外添加的头结点
		* @param:         appendXml 想要在后面添加的xml内容
		* @return:        	 得到的xml字符串
		* @Author:        宗宁
		* @Create Time:   2015年01月10日
	*/
	public static String buildRequestXmlByMap(Map<String,String> mapParam, String topNodeName, String appendXml){
		StringBuilder sb = new StringBuilder();
		
		if (mapParam != null) {
			
			if (topNodeName != null && !"".equals(topNodeName)) {
				sb.append("<").append(topNodeName).append(">");
			}
			else {
				sb.append("<Request>");
			}
			
			Iterator<String> it = mapParam.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				String v = mapParam.get(key);
				sb.append("<").append(key).append(">");
				sb.append(v);
				sb.append("</").append(key).append(">");
			}
			
			if (appendXml != null && !"".equals(appendXml)) {
				sb.append(appendXml);
			}
			
			if (topNodeName != null && !"".equals(topNodeName)) {
				sb.append("</").append(topNodeName).append(">");
			}
			else {
				sb.append("</Request>");
			}
		}
		else {
			sb.append("<Request></Request>");
		}

		return sb.toString();
	}
	
	
	// ------------------- start by blank ---------------------
	@SuppressWarnings("rawtypes")
	public static <T> void copyDataToTargetBean(T targetObj, String xml) throws DocumentException,Exception{	
		Map map = pasrePropertyXMLToMap(targetObj.getClass(),xml);
		copyProperty(targetObj,map);
	}
	
}
