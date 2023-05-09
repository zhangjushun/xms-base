package com.xms.common.utils.util;

import java.util.*;

public class CollectionUtil {
	
	  //并且key转成小写
	  public static List<Map<String,Object>> transferListWithLowerKey(List<Map<String,Object>> list) {
		  List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		  	for(Map<String,Object> map :list) {
		  		Map<String,Object> tempMap = new HashMap<>();
		  		Iterator<String> it = map.keySet().iterator();
		  		while(it.hasNext()) {
		  			String key = it.next();
		  			tempMap.put(key.toLowerCase(), map.get(key)==null?"":map.get(key));
		  		}
		  		result.add(tempMap);
		  	}
		  return result;
	  }

	  public static Map<String,Object> transferMapWithLowerKey(Map<String,Object> map){
			Map<String,Object> tempMap = new HashMap<>();
	  		Iterator<String> it = map.keySet().iterator();
	  		while(it.hasNext()) {
	  			String key = it.next();
	  			tempMap.put(key.toLowerCase(), map.get(key)==null?"":map.get(key));
	  		}
	  		return tempMap;
	  }
	  
	  
}
