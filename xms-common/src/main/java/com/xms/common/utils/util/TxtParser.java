package com.xms.common.utils.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TxtParser {
	public static Map<String,Object>  readTxt(String filePath){ 
		List<Map<String,Object>> lmList= new ArrayList<>();
		List<String> dateList = new ArrayList<>();
        try{
        	File file = new File(filePath);
        	String fileName = file.getName();//获取文件名
        	 
        	InputStream is = new FileInputStream(file);
        	BufferedInputStream bufferin = new BufferedInputStream(is,500*1024);
    		String code = ZipParser.analysisCode(bufferin, 0);
    		
        	BufferedReader br = new BufferedReader(new InputStreamReader(bufferin,code));
//            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            String startdate = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行              
//                s = s.replace(" ", "").replace("\t", "");
                if("".equals(s)){
                	continue;
                }
                if(s.length()<=11 && s.contains("/")){
                	startdate = "20" + s.replace("/", "-").substring(s.length()-8, s.length()); 
                	dateList.add(startdate);
                }else{    
                	String[] split = s.split(" "); 
                	String starttime = split[0]+"00:00:00".substring(split[0].length(), 8);
                	
                	String title 	 = split[1];
                	Map<String,Object> map = new HashMap<>();
                	
                	map.put("startdate",startdate);
                	map.put("enddate",startdate);
                	map.put("starttime",starttime);
                	map.put("title", title);
                	map.put("fileName", fileName);
                	lmList.add(map);
                }
            }
            br.close();        
        }catch(Exception e){
            e.printStackTrace();
        }   
        
        for (int i = 0; i < lmList.size()-1; i++) {
        	Map<String, Object> map  = lmList.get(i);
        	Map<String, Object> map1 = lmList.get(i+1);
        	
        	String startdate  = map.get("startdate")+"";
        	String startdate1 = map1.get("startdate")+"";
        	
        	if(startdate.equals(startdate1)){
        		map.put("stoptime", map1.get("starttime"));        		
        	}else{
        		map.put("stoptime", "23:59:59");
        	}   	
		}
        Map<String, Object> map = lmList.get(lmList.size()-1);
        map.put("stoptime", "23:59:59");   
        
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("lmList", lmList);
        resultMap.put("dateList", dateList);
        
		return resultMap;
	}
	
	 
}
