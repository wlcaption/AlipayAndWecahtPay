package com.qianhuan.yxgsd.holagames.net;

import java.util.List;

import com.alibaba.fastjson.JSON;


 /**
  * 阿里巴巴Json工具
  * 
 * @author 小魏 
 * @Email: wlcaption@gmail.com
 * 2017-2-13 下午3:16:07
 */
public class Json {
	
	/**
	 * 将字符串转化为对应的对象
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
	 * 将对象转换为字符串
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
	 * 将字符串转换为对象List对象
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
