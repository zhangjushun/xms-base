package com.xms.common.utils.util;

import java.util.Map;

/**
 * 
 * @Title:  AttributeUtil.java    
 * @Description:       
 * @author: xms     
 * @date:   2018年2月6日 上午10:21:36
 */
public class AttributeUtil {
	
	public static String getString(Map<String, Object> m, String key){
		Object o = m.get(key);
		if(o==null)
			return "";
		
		return o.toString();
	}
	
	public static int getInt(Map<String, Object> m, String key, int defaultValue){
		Object o = m.get(key);
		if(o==null)
			return defaultValue;
		
		int b = defaultValue;
		try {
			b = (int)Float.parseFloat(o.toString());
		} catch (NumberFormatException e) {
		}
		return b;
	}
	

}
