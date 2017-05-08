package com.holagames.xcds.net;

import java.util.List;

import com.alibaba.fastjson.JSON;


 /**
  * ����Ͱ�Json����
  * 
 * @author Сκ 
 * @Email: wlcaption@gmail.com
 * 2017-2-13 ����3:16:07
 */
public class Json {
	
	/**
	 * ���ַ���ת��Ϊ��Ӧ�Ķ���
	 * 
	 * @param str
	 * @param beanObj
	 * @return
	 */
	
	public static <T> T StringToObj(String str, Class<T> beanObj){
		try {
			return JSON.parseObject(str, beanObj);
		} catch (Exception e) {
				return null;
		}
	}
	
	/***
	 * ������ת��Ϊ�ַ���
	 * 
	 * @param object
	 * @return
	 */
	
	public static String ObjToString(Object object){
		try {
			return JSON.toJSONString(object);
		} catch (Exception e) {
			return null;
		}
	}
	
	/***
	 * ���ַ���ת��Ϊ����List����
	 * @param str
	 * @param beanObj
	 * @return
	 */
	public static <T> List<T> StringToList(String str, Class<T> beanObj){
		try {
			return JSON.parseArray(str, beanObj);
		} catch (Exception e) {
			return null;
		}
	}
	
	

}
