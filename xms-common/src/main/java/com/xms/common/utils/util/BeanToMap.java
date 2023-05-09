package com.xms.common.utils.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.*;

public class BeanToMap {

    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性  
                if (!key.equals("class")) {
                    // 得到property对应的getter方法  
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }
        return map;
    }

//	public static void transMap2Bean(Object obj, Map map){
//		if(obj==null || map==null){
//			return ;
//		}
//		try {
//			BeanUtils.populate(obj, map);
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();
                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    Class<?> propertyType = property.getPropertyType();
                    if (propertyType.isInstance(new Date())) {
                        Date data = DateUtil.stampToDate(String.valueOf(value));
                        setter.invoke(obj, data);
                        continue;
                    } else if (propertyType.getName().equalsIgnoreCase("java.lang.Long")) {
                        Long data = Long.valueOf(String.valueOf(value));
                        setter.invoke(obj, data);
                        continue;
                    } else if (propertyType.getName().equalsIgnoreCase("java.lang.Integer")) {
                        Integer data = 0;
                        if (!String.valueOf(value).trim().isEmpty()) {
                            data = Integer.valueOf(String.valueOf(value));
                        }
                        setter.invoke(obj, data);
                        continue;
                    }else if (propertyType.getName().equalsIgnoreCase("java.lang.Byte")) {
                        Byte data = 0;
                        if (!String.valueOf(value).trim().isEmpty()) {
                            data = Byte.valueOf(String.valueOf(value));
                        }
                        setter.invoke(obj, data);
                        continue;
                    }
                    if (value instanceof Integer){
                        value = value+"";
                    }
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return;

    }


    public static Map<String, Object> formatLogCondition(String key, String operator, Object value) {
        Map<String, Object> cond = new HashMap<>();
        cond.put("key", key);
        cond.put("value", value);
        cond.put("operator", operator);
        return cond;
    }


    public static <T> List<Map<String, Object>> objectsToMaps(List<T> objList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (objList != null && objList.size() > 0) {
            Map<String, Object> map = null;
            T bean = null;
            for (int i = 0, size = objList.size(); i < size; i++) {
                bean = objList.get(i);
                map = transBean2Map(bean);
                list.add(map);
            }
        }
        return list;
    }
}
