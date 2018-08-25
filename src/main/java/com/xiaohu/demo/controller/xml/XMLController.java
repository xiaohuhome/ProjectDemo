package com.xiaohu.demo.controller.xml;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaohu.demo.entity.user.User;
import com.xiaohu.demo.utils.result.RequestResult;
import com.xiaohu.demo.utils.xml.XmlToBeanUtil;

@Controller
@RequestMapping("/xml")
public class XMLController {
 /** 
   * 方法名:          parseXML
   * 方法功能描述:     解析XML
   */
	@PostMapping(value = "/parseXML")
	@ResponseBody
	public RequestResult parseXML(@RequestBody String xml) {
		RequestResult result = new RequestResult();
		if (xml!=null && (xml.indexOf("<ResultCode></ResultCode>")>=0 || xml.indexOf("<ResultDesc></ResultDesc>")>=0)) {
			result.setMsg("fail:ResultCode或ResultDesc节点值必须为0");
			result.setStatus("-1");
			result.setSuccess(false);
			return result;
		}

		List<User> list = XmlToBeanUtil.parseBeansToList(User.class, xml);

		if (list.size() == 0) {
			result.setMsg("fail:xml数据不完整");
			result.setStatus("-1");
			result.setSuccess(false);
			return result;
		}
		result.setMsg("解析成功");
		result.setStatus("200");
		result.setData(list);
		result.setSuccess(true);
		return result;  
	} 
	
	/**
	 * request:http://localhost:8080/ProjectDemo/xml/parseXML
	 * data:
	 * <Response>
		  <ResultCode>0</ResultCode>
		  <ResultDesc>0</ResultDesc>
		  <ResultList>
		    <User>
		      <id>CT20170101002</id>
		      <username>zhangsan1</username>
		      <password>1234567</password>
		      <gender>0</gender>
		      <age>18</age>
		      <telephone>13475644435</telephone>
		      <address>山东济南</address>
		      <birthDate>1995-02-03</birthDate>
		      <createDate>2018-07-23</createDate>
		      <createTime>2018-07-23 20:14:23</createTime>
		      <disabled>0</disabled>
		      <uuid>1234567</uuid>
		    </User>
		  </ResultList>
		</Response>
	 * response:
	 * {
		  "msg": "解析成功",
		  "status": "200",
		  "data": [
		    {
		      "id": "CT20170101002",
		      "username": "zhangsan1",
		      "password": "1234567",
		      "gender": "0",
		      "age": "18",
		      "telephone": "13475644435",
		      "address": "山东济南",
		      "birthDate": "1995-02-03",
		      "createDate": "2018-07-23",
		      "createTime": "2018-07-23 20:14:23",
		      "disabled": "0",
		      "uuid": "1234567"
		    }
		  ],
		  "success": true
		}
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * */
}
