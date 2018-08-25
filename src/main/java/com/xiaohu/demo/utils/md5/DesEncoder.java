package com.xiaohu.demo.utils.md5;

import java.security.MessageDigest;

/**
 * 
 * md5加密类，不能解密
 * 
 * @author 赵文祥
 * @since 1.0.0
 * 
 */
public class DesEncoder {

	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'A', 'B', 'C', 'D', 'E', 'F' };
	
	private static final String  CHARSET = "UTF-8";
	/**
	 * 
	* 方法名:          encrypt
	* 方法功能描述:    加密，当前系统采用此方法
	* 解密方法对应着decrypt(String pw)
	* @param:         
	* @return:        
	* @Author:        杨雄雄
	* @Create Date:   2014-6-18 下午1:53:00
	 */
	public static String encrypt(String pw) {
		return encrypt(pw,CHARSET);
	}

	/**
	 * 
	* 方法名:          isPasswordValid
	* 方法功能描述:		判断密码加密后是否与密文相同
	* @param	encPass 加密后的密文
	* @param 	rawPass 加密前的字符串    	
	* @return:        其他非简体汉字返回 '0';
	* @Author:        杨雄雄
	* @Create Date:   2014-6-10 上午10:16:20
	 */
	public static boolean isPasswordValid(String encPass, String rawPass) {
		String encRawPass = encrypt(rawPass,CHARSET);
		
		return encPass.equals(encRawPass);
	}
	
	/**
	 * 
	* 方法名:          encrypt
	* 方法功能描述:    md5加密字符串
	* @param:         
	* @return:        
	* @Author:        杨雄雄
	* @Create Date:   2014-7-29 下午9:45:31
	 */
	public final static String encrypt(String s,String encoding) {
		try {
			byte[] strTemp = s.getBytes(encoding);
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	
	//生成MD5  
    public static String getMD5(String message) {  
        String md5 = "";  
        try {  
            MessageDigest md = MessageDigest.getInstance("MD5");  // 创建一个md5算法对象  
            byte[] messageByte = message.getBytes("UTF-8");  
            byte[] md5Byte = md.digest(messageByte);              // 获得MD5字节数组,16*8=128位  
            md5 = bytesToHex(md5Byte);                            // 转换为16进制字符串  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return md5;  
    }  
   
     // 二进制转十六进制  
    public static String bytesToHex(byte[] bytes) {  
        StringBuffer hexStr = new StringBuffer();  
        int num;  
        for (int i = 0; i < bytes.length; i++) {  
            num = bytes[i];  
             if(num < 0) {  
                 num += 256;  
            }  
            if(num < 16){  
                hexStr.append("0");  
            }  
            hexStr.append(Integer.toHexString(num));  
        }  
        return hexStr.toString().toUpperCase();  
    }  
    
    public static void main(String[] args) {
		System.out.println(encrypt("123456"));
	}
}
