package com.xiaohu.demo.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


/**
 * @author Tom
 */
public class DateUtils {
	private StringBuffer buffer = new StringBuffer();
	private static String ZERO = "0";
	private static DateUtils date;
	public static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	public static SimpleDateFormat format3 = new SimpleDateFormat("yyyyMM");
	public static SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	private static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyy-MM-dd HH:mm");  
	private static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	private static SimpleDateFormat yyyyZNMMZN = new SimpleDateFormat("yyyy年MM月");
	private static SimpleDateFormat MMZNdd = new SimpleDateFormat("MM月dd");
	private static SimpleDateFormat HHmm = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
	
	public static String yyyyMMddHHmmss1(Date date) {
		return format2.format(date);
	}
	
	public static String yyyyMM(Date date) {
		return format3.format(date);
	}
	
	public static String yyyyMMdd1(Date date) {
		return format.format(date);
	}
	
	public String getNowString() {
		Calendar calendar = getCalendar();
		buffer.delete(0, buffer.capacity());
		buffer.append(getYear(calendar));

		if (getMonth(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMonth(calendar));

		if (getDate(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getDate(calendar));
		if (getHour(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getHour(calendar));
		if (getMinute(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMinute(calendar));
		if (getSecond(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getSecond(calendar));
		return buffer.toString();
	}

	private static int getDateField(Date date,int field) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c.get(field);
	}
	public static int getYearsBetweenDate(Date begin,Date end){
		int bYear=getDateField(begin,Calendar.YEAR);
		int eYear=getDateField(end,Calendar.YEAR);
		return eYear-bYear;
	}
	
	public static int getMonthsBetweenDate(Date begin,Date end){
		int bMonth=getDateField(begin,Calendar.MONTH);
		int eMonth=getDateField(end,Calendar.MONTH);
		return eMonth-bMonth;
	}
	public static int getWeeksBetweenDate(Date begin,Date end){
		int bWeek=getDateField(begin,Calendar.WEEK_OF_YEAR);
		int eWeek=getDateField(end,Calendar.WEEK_OF_YEAR);
		return eWeek-bWeek;
	}
	public static int getDaysBetweenDate(Date begin,Date end){
		int bDay=getDateField(begin,Calendar.DAY_OF_YEAR);
		int eDay=getDateField(end,Calendar.DAY_OF_YEAR);
		return eDay-bDay;
	}
	
	/**
	 * 获取date年后的amount年的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, amount);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date年后的amount年的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficYearEnd(Date date,int amount) {
		Date temp = getStartDate(getSpecficYearStart(date,amount + 1));
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date月后的amount月的第一天的开始时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, amount);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取当前自然月后的amount月的最后一天的终止时间
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficMonthEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getSpecficMonthStart(date,amount + 1));
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFinallyDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的开始时间（这里星期一为一周的开始）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekStart(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return getStartDate(cal.getTime());
	}

	/**
	 * 获取date周后的第amount周的最后时间（这里星期日为一周的最后一天）
	 * 
	 * @param amount
	 *            可正、可负
	 * @return
	 */
	public static Date getSpecficWeekEnd(Date date,int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY); /* 设置一周的第一天为星期一 */
		cal.add(Calendar.WEEK_OF_MONTH, amount);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return getFinallyDate(cal.getTime());
	}
	
	public static Date getSpecficDateStart(Date date,int amount){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, amount);
		return getStartDate(cal.getTime());
	}
	
	public static String getNextDate(String strDate) {
		Date nextDate = DateUtils.getSpecficDateStart(DateUtils.getDate(strDate), 1);

		return DateUtils.formatDate(nextDate, "yyyy-MM-dd");		
	}

	/**
	 * 得到指定日期的一天的的最后时刻23:59:59
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFinallyDate(Date date) {
		String temp = format.format(date);
		temp += " 23:59:59";

		try {
			return format1.parse(temp);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到指定日期的一天的开始时刻00:00:00
	 * 
	 * @param date
	 * @return
	 */
	public static Date getStartDate(Date date) {
		String temp = format.format(date);
		temp += " 00:00:00";

		try {
			return format1.parse(temp);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获得两个日期之间所有的日期.
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static List<String> getDateList(Date date1, Date date2) {
		List<String> lstDate = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long end = date2.getTime();
	
		lstDate.add(sdf.format(date1));
		
		while (true) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
			long n = cal.getTime().getTime();
			
			if (n <= end) {
				lstDate.add(sdf.format(cal.getTime()));
			}
			else {
				break;
			}
		}	
		
		return lstDate;
	}
	
	public static String getCommonDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.format(date);
	}

	private int getYear(Calendar calendar) {
		return calendar.get(Calendar.YEAR);
	}

	private int getMonth(Calendar calendar) {
		return calendar.get(Calendar.MONDAY) + 1;
	}

	private int getDate(Calendar calendar) {
		return calendar.get(Calendar.DATE);
	}

	private int getHour(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	private int getMinute(Calendar calendar) {
		return calendar.get(Calendar.MINUTE);
	}

	private int getSecond(Calendar calendar) {
		return calendar.get(Calendar.SECOND);
	}

	private static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	public static DateUtils getDateInstance() {
		if (date == null) {
			date = new DateUtils();
		}
		return date;
	}
	
	public String getNowDateString(){
		Calendar calendar = getCalendar();
		buffer.delete(0, buffer.capacity());
		buffer.append(getYear(calendar));

		if (getMonth(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getMonth(calendar));

		if (getDate(calendar) < 10) {
			buffer.append(ZERO);
		}
		buffer.append(getDate(calendar));
		
		return buffer.toString();
	}
	
	public String getNowTimeString(){
		Calendar calendar = getCalendar();	
		String hour=Integer.toString(getHour(calendar));
		String minu=Integer.toString(getMinute(calendar));
		String seconds=Integer.toString(getSecond(calendar));
		//hh:mm:ss
		String time=hour+":"+minu+":"+seconds;
		
		return time;
	}
	
	public static String formatDate(Date date,String fmt){
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		String dateStr = sdf.format(date);
		return dateStr;
	}	
	
	/**
	 * 获得当前日期.
	 * 
	 * @return
	 */
	public static Date getNowDate(){
		Calendar calendar = getCalendar();
		
		return calendar.getTime();
	}
	
	/**
	 * 获得开始日期.
	 * 
	 * @param beginDate
	 */
	public static Date getBeginDate(Date beginDate) {
		Date now = DateUtils.getNowDate();
		
		if (beginDate == null) {
			beginDate = getSpecficDateStart(now, -180);
		}
		
		return beginDate;	
	}
	
	/**
	 * 获得结束日期.
	 * 
	 * @param endDate
	 */
	public static Date getEndDate(Date endDate) {
		Date now = DateUtils.getNowDate();
		
		if (endDate == null) {	
			endDate = now;
		}	
		
		return endDate;	
	}
	
	/**
	 * 获得结束日期.
	 * 
	 * @param endDate
	 */
	public static Date getEndDate(String strDate) {
		Date date = DateUtils.getDate(strDate);		
		
		return DateUtils.getEndDate(date);
	}	
	
	/**
	 * 获得当前日期后n天的日期.
	 * 
	 * @param n
	 *           
	 * @return
	 */
	public static String getNowSpecficDate(int n) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, n);
		
		String date = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		String dayOfWeek = "";
		
		switch (cal.get(Calendar.DAY_OF_WEEK)-1) {
			case 0 : dayOfWeek = "星期日"; break;
			case 1 : dayOfWeek = "星期一"; break;
			case 2 : dayOfWeek = "星期二"; break;
			case 3 : dayOfWeek = "星期三"; break;
			case 4 : dayOfWeek = "星期四"; break;
			case 5 : dayOfWeek = "星期五"; break;
			case 6 : dayOfWeek = "星期六"; break;
			default : break;
		}
		
		return date + "," + dayOfWeek;
	}	
	
	public static String getNowStrDate() {
		Date date = getNowDate();
		
		return getDateStr(getCommonDate(date));
	}
	

	/**
	 * 根据yyyy-MM-dd格式的日期字符串获得yyyy年MM月dd日的字符串.
	 * 
	 * @param strDate
	 * @return
	 */
	public static String getDateStr(String strDate) {
		
		if (strDate.length() != 10) {
			return null;
		}
		
		return strDate.substring(0, 4) + "年" + strDate.substring(5, 7) + "月" + strDate.substring(8, 10) + "日";
	}	
	
	/**
	 * 根据String类型的日期对象获得Date类型的日期对象.
	 * 
	 * @param strDate 格式为yyyy-MM-dd
	 * @return
	 */
	public static Date getDate(String strDate) {
		
		if (StringUtils.isNotEmpty(strDate)) {
			String[] temp = strDate.split("-");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.parseInt(temp[0]));
			cal.set(Calendar.MONTH, Integer.parseInt(temp[1])-1);
			cal.set(Calendar.DATE, Integer.parseInt(temp[2]));
			
			return cal.getTime();
		}
		else {
			return null;
		}
	}	
	
	/** 
	 * 方法名:          getAge
	 * 方法功能描述:    根据日期计算年龄
	 * @param:         是包含汉字的字符串
	 * @return:        其他非简体汉字返回 '0';
	 * @Author:        blank
	 * @Create Date:   2014年6月25日 上午9:23:21
	 */
	public static Long getAge(Date birthDate) {
		
		if(birthDate==null){
			return 0L;
		}
		Date newBirt = birthDate;
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
		return (long)age;
		
	}
	
	/** 
	* 方法名:          getTimeInterval
	* 方法功能描述:      计算时间间隔，根据间隔时长不同，单位不同
	* @param:         
	* @return:        
	* @Author:        lmx
	* @Create Date:   2014年8月14日 下午3:59:49
	*/
	public static String getTimeInterval(Date date) {
		String result = "";
		long interval = System.currentTimeMillis() - date.getTime();
		
		interval = interval / 1000 / 60;
		
		if (interval <= 1) {
			result = "1分钟";
		}
		else if (interval < 60) {
			result = interval + "分钟";
		}
		
		if (!"".equals(result)) {
			return result;
		}
		
		interval = interval / 60;
		
		if (interval < 24) {
			return interval + "小时";
		}
		
		// 以下计算相差天数
		Calendar cnow = Calendar.getInstance();
		Calendar cdate = Calendar.getInstance();
		cdate.setTime(date);
		
		interval = cnow.get(Calendar.DAY_OF_YEAR) - cdate.get(Calendar.DAY_OF_YEAR);
		
		if (interval <= 7) {
			return interval + "天";
		}
		
		interval = cnow.get(Calendar.WEEK_OF_YEAR) - cdate.get(Calendar.WEEK_OF_YEAR);
		
		if (interval <= 4) {
			return interval + "周";
		}
		
		interval = cnow.get(Calendar.MONTH) - cdate.get(Calendar.MONTH);
		
		if (interval <= 12) {
			return interval + "月";
		}

		interval = cnow.get(Calendar.YEAR) - cdate.get(Calendar.YEAR);
		
		if (interval > 0) {
			return interval + "年";
		}
		
		return result;
	}
	
	public static String getCurrentDateTimeStr(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String str = sdf.format(date);
		
		return str;
	}
	
	/**
	 * 
	* 方法名:          getDateIntevalDesc
	* 方法功能描述:    根据当前时间和参数时间比较，然后返回它们之间的距离描述信息，比如“刚刚”、“一小时前”等
	* @param:         
	* @return:        
	* @Author:        杨雄雄
	* @Create Date:   2014-9-15 下午1:37:18
	 */
	public static String getDateIntevalDesc(Date date){
		  String interval = null;  
	        long time = System.currentTimeMillis() - date.getTime();// 得出的时间间隔是毫秒  
	          
	        if(time/1000 < 10 ) {  
	        //如果时间间隔小于10秒则显示“刚刚”time/10得出的时间间隔的单位是秒  
	            interval ="刚刚";  
	        } else if(time/1000 < 60 && time/1000 > 0) {  
	        //如果时间间隔小于60秒则显示多少秒前  
	            int se = (int) ((time%60000)/1000);  
	            interval = se + "秒前";  
	              
	        } else if(time/60000 < 60 && time/60000 > 1) {  
	        //如果时间间隔小于60分钟则显示多少分钟前  
	            int m = (int) ((time%3600000)/60000);//得出的时间间隔的单位是分钟  
	            interval = m + "分钟前";  
	              
	        }else if(time/3600000 < 24 && time/3600000 >= 1) {  
	        //如果时间间隔小于24小时则显示多少小时前  
	            int h = (int) (time/3600000);//得出的时间间隔的单位是小时  
	            interval = h + "小时前";  
	              
	        } else {  
	            //大于24小时，则显示正常的时间，但是不显示秒  
	            interval = yyyyMMddHHmm.format(date);  
	        }  
	        return interval;  
	}
	
	/**
	 * 
	* 方法名:          yyyyMMdd
	* 方法功能描述:    返回格式为YYYY-MM-DD
	* @param:         
	* @return:        
	* @Author:        杨雄雄
	* @Create Date:   2014-9-15 下午8:54:34
	 */
	public static String yyyyMMdd(Date date){
		
		if (date == null) {
			return null;
		}
		
		return yyyyMMdd.format(date);
	}
	
	/**
	 * 
	* 方法名:          yyyyMMddHHmm
	* 方法功能描述:    返回格式为YYYY-MM-DD HH:mm
	* @param:         
	* @return:        
	* @Author:        杨雄雄
	* @Create Date:   2014-9-15 下午8:54:34
	 */
	public static String yyyyMMddHHmmss(Date date){
		return yyyyMMddHHmmss.format(date);
	}
	
	public static String yyyyMMddHHmm(Date date){
		return yyyyMMddHHmm.format(date);
	}
	
	/**
	 * 
	* 方法名:          yyyyZNMMZN
	* 方法功能描述:    返回格式为yyyy年MM月
	* @param:         
	* @return:        
	* @Author:        杨雄雄
	* @Create Date:   2014年10月29日 下午8:35:12
	 */
	public static String yyyyZNMMZN(Date date){
		return yyyyZNMMZN.format(date);
	}
	/**
	 * 
	* 方法名:          yyyyZNMMZN
	* 方法功能描述:    返回格式为yyyy年MM月
	* @param:         
	* @return:        
	* @Author:        杨雄雄
	* @Create Date:   2014年10月29日 下午8:35:12
	 */
	public static String MMZNdd(Date date){
		return MMZNdd.format(date);
	}
	
	/**
	 * 方法名称: yyyy
	 * 方法功能描述：
	 * @param: 
	 * @return: String
	 * @Author: 付晓
	 * @Create Date: 2017年7月6日下午12:23:11
	 * @Remarks: Autogenerated, do not edit. All changes will be undone.
	 */
	public static String yyyy(Date date){
		return yyyy.format(date);
	}
	
	/**
	 * 
	* 方法名:          HHmm
	* 方法功能描述:   返回格式为HH:mm 
	* @param:         
	* @return:        
	* @Author:        杨雄雄
	* @Create Date:   2014年10月31日 上午8:55:31
	 */
	public static String HHmm(Date date){
		return HHmm.format(date);
	}
	
	/**
	 * endDate 减 beginDate的差值
	 * 
	 * */
	public static int daysBetween(Date beginDate,Date endDate) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			beginDate = sdf.parse(sdf.format(beginDate));
			endDate = sdf.parse(sdf.format(endDate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			long begInTime = cal.getTimeInMillis();
			cal.setTime(endDate);
			long endTime = cal.getTimeInMillis();
			long between_days = (endTime - begInTime) / (1000 * 3600 * 24);
			return Integer.parseInt(String.valueOf(between_days));
		} catch(Exception e){
			return -1;
		}
	}
	
	/**
	 * 计算两个日期之间的周的差值，7天之内的结果为0
	 * 
	 * */
	public static int daysBetweenByWeek(Date beginDate,Date endDate) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			beginDate = sdf.parse(sdf.format(beginDate));
			endDate = sdf.parse(sdf.format(endDate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(beginDate);
			long begInTime = cal.getTimeInMillis();
			cal.setTime(endDate);
			long endTime = cal.getTimeInMillis();
			long between_days = (endTime - begInTime) / (1000 * 3600 * 24 * 7);
			return Integer.parseInt(String.valueOf(between_days));
		} catch(Exception e){
			return -1;
		}
	}
	
	public static String getCurrentDateStr() {
		Date date = new Date();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static Date parseDate(String strDate, String format) {
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
    try {
      date = dateFormat.parse(strDate);
    } 
    catch (Exception e) {
      e.printStackTrace();
    }	
    
    return date;
	}
	
	/**
	 * 根据字符串返回日期，字符串必须是10位的，但链接符号可以任意
	 * */
	public static Date getDateByString(String dateString){
		if(dateString == null || "".equals(dateString)){
			return null;
		}
		Date newDate = null;
		try {
			newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
		} catch (ParseException e) {
			try{
				if (dateString != null && dateString.length() >= 10) {
					newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString.substring(0, 4) + "-" + dateString.substring(5, 7) + "-" + dateString.substring(8, 10));
				}
			} catch(ParseException e2){
			}
		}
		return newDate;
	}

	/**
	 * <p>方法名：birthDate2Age</p>
	 * <p>功能描述：根据生日计算年龄</p>
	 * <p>计算标准：当天日期与出生日期的天数差除以365，取整数部分
	 * @Author .Mark
	 * @Date   2018年1月9日
	 * @Return String
	 * <p>备注：健康乐随访平台统一用这种计算标准</p>
	 */
	public static String birthDate2Age(Date birthDate) {
		if(birthDate == null) { return ""; }
		return String.valueOf((new Date().getTime() - birthDate.getTime())/1000/60/60/24/365);
	}


	/**
	 * 方法名称:compareDate
	 * 方法功能描述:data1>date2,返回true。否则返回false
	 * @Param: [date1, date2]
	 * @return: java.lang.Boolean
	 * @Author: Mr.Fu
	 * @Create Date: 2018年04月08日 10:37:48
	 * @Remarks: Autogenerated, do not edit. All changes will be undone.
	 */
	public static Boolean compareDate(Date date1,Date date2){
		Boolean result = false;
		if(date1.getTime()>date2.getTime()){
			result = true;
		}
		return  result;
	}
	
	/**
	 * 方法名称:getNewDate
	 * 方法功能描述:返回String数组最新日期
	 */
	public static String getNewDate(String str ,String m) {
		if(str!=null && !"".equals(str)) {
			String strs[] = str.split(m);
			Date newDate = getDateByString(strs[0]);
			for(String s:strs) {
				if(newDate.before(getDateByString(s))) {
					newDate = getDateByString(s);
				}
			}
			return yyyyMMdd(newDate);
		}
		return "";
	}
}
