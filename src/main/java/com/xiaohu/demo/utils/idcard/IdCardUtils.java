package com.xiaohu.demo.utils.idcard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
* <p>标题: IdCardUtils.java</p>
* <p>业务描述: </p>
* <p>公司: 东华软件股份公司</p>
* <p>版权: dhcc2015</p>
* @author: 王子亚
* @date: 2015年6月5日
* @version: V1.0
 */
public class IdCardUtils {
	
	/**
	 * 
		* 方法名:          getAgeByBirthday
		* 方法功能描述:     出生日期获取年龄
		* @params:
		* @return:        String
	 */
	public static String getAgeByBirthday(String birthday) {
		if(birthday==null||"".equals(birthday)){
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newBirt = null;
		try {
			newBirt = sdf.parse(birthday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();

		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(newBirt);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth 
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth 
				age--;
			}
		}
		if (age <= 0) {
			age = 1;
		}
		return String.valueOf(age);
	}
    

    /******************身份证的有效验证--start********************************
     * 
    	* 方法名:          IDCardValidate
    	* 方法功能描述:     身份证的有效验证
    	* @params:
    	* @return:        String
     */
    
    public static String IDCardValidate(String IDStr) throws ParseException {
        String errorInfo = "";// 记录错误信息
        if(IDStr!=null) {
			IDStr = IDStr.toUpperCase();
		}
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2" };
        String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2" };
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr==null||(IDStr.length() != 15 && IDStr.length() != 18)) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                            strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // ================ 地区码时候有效 ================
        Hashtable<String, String> h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        return "";
    }
    
    /**
     * 
    	* 方法名:          GetAreaCode
    	* 方法功能描述:     设置地区编码
    	* @params:
    	* @return:        Hashtable
     */
    private static Hashtable<String, String> GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }
    
    /**
     * 
    	* 方法名:          isNumeric
    	* 方法功能描述:     判断字符串是否为数字
    	* @params:
    	* @return:        boolean
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * 
    	* 方法名:          isDate
    	* 方法功能描述:     (判断字符串是否为日期格式) 
    	* @params:
    	* @return:        boolean
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
    /**********身份证的有效验证--end********************************************************************/
    
    /**
     * 
    	* 方法名:          getBirthdayByIdCard
    	* 方法功能描述:     根据身份证号获取出生日期
    	* @params:
    	* @return:        Date
     */
    public static Date getBirthdayByIdCard(String idCard){
    	String dateStr= "";
    	SimpleDateFormat sdf = null;
    	if(idCard.length()==18){
    		dateStr=idCard.substring(6,14);
    		sdf = new SimpleDateFormat("yyyyMMdd");
    	}
    	else if(idCard.length()==15){
    		dateStr=idCard.substring(6,12);
    		sdf = new SimpleDateFormat("yyMMdd");
    	}
    	Date date = new Date();
    	try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
    	return date;
    }
    
    /**
     * 
    	* 方法名:          getGenderCodeByIdCard
    	* 方法功能描述:     根据身份证号获取性别(1男2女)
    	* @params:
    	* @return:        String
     */
    public static String getGenderCodeByIdCard(String idCard){
    	String genderStr="";
    	if(idCard.length()==15){
    		genderStr=idCard.substring(14,15);
    	}
    	else if(idCard.length()==18){
    		genderStr=idCard.substring(16,17);
    	}
    	try{
    		if(Integer.parseInt(genderStr)%2!=0){
    			return "1";
    		}
    		else return "2";
    	}catch(Exception e){
    		return null ;
    	}
    	
    }
}
