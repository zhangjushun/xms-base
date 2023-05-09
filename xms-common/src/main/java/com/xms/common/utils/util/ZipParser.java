package com.xms.common.utils.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ZipParser {
	public static Map<String, Object> readZip(String fileName,InputStream is) throws IOException { 
		List<Map<String,Object>> columnList= new ArrayList<>();//栏目列表
		List<Map<String,Object>> startdateList= new ArrayList<>();//频道开始日期列表
		
		
		
//		List<String> startdateList = new ArrayList<>();//频道开始日期列表
			
		BufferedInputStream bufferin = new BufferedInputStream(is,500*1024);
		String code = analysisCode(bufferin, 0);
			
		BufferedReader bf = new BufferedReader(new InputStreamReader(bufferin, code));
        String readLine ="";
        String pdStartdate = "";//频道开始日期	     
        
		while((readLine = bf.readLine())!=null){
//				readLine = readLine.replace(" ", "").replace("\t", "");
			readLine = readLine.trim().replace("'", "\\'");
		 
			
			if("".equals(readLine)){
				continue;
			}
			if(readLine.indexOf(":")==-1 && readLine.indexOf("/")>0){	            	 
				pdStartdate = readLine.replace("/", "-").substring(readLine.length()-8, readLine.length());
				pdStartdate = "20"+pdStartdate;
				
				Map<String,Object> map = new HashMap<>();
				map.put("pdStartdate", pdStartdate);
				map.put("channelName", fileName);
				startdateList.add(map); 
			}else if(readLine.indexOf(":")>0 && readLine.indexOf("/")==-1){             	
				String[] split = readLine.split(" ");	     
				String lmStarttime = split[0]+"00:00:00".substring(split[0].length(), 8);
				String title 	 = split[1];
				Map<String,Object> map = new HashMap<>();
         	
	         	map.put("pdStartdate",pdStartdate);//频道开始日期
	         	map.put("lmStarttime",lmStarttime);//栏目开始时间
	         	map.put("title", title);
	         	map.put("fileName", fileName);
	         	columnList.add(map);
			}
        }
		bf.close();
		is.close();
			
			
			
		
		Map<String, Object> map0 = columnList.get(0);
		map0.put("lmStartdate", map0.get("pdStartdate"));		
		
        for (int i = 0; i < columnList.size()-1; i++) {
        	Map<String, Object> map  = columnList.get(i);
        	Map<String, Object> map1 = columnList.get(i+1);
        	map.put("lmStoptime", map1.get("lmStarttime")); 
        	
        	String lmStartdate  = map.get("lmStartdate")+"";//栏目开始日期
        	
        	String lmStarttime  = map.get("lmStarttime")+"";//栏目开始时间
        	String lmStoptime   = map.get("lmStoptime")+""; //栏目结束时间
        	
    		String date1 = lmStartdate+" "+lmStarttime;
    		String date2 = lmStartdate+" "+lmStoptime;
    		
    		Integer compar = DateCalculateUtil.compareDate(date1, date2);
    		if(compar==-1 ||compar==0){//后面时间大
    			map.put("lmEnddate", lmStartdate);
    		}else{//前面时间大
    			map.put("lmEnddate", DateCalculateUtil.nextDate(lmStartdate));
    		}
    		
    		String pdStartdate0 = map.get("pdStartdate")+"";//频道开始日期
    		String pdStartdate1 = map1.get("pdStartdate")+"";
    		
        	
        	if(!pdStartdate0.equals(pdStartdate1)){
        		map1.put("lmStartdate", pdStartdate1);
        	}else{
        		map1.put("lmStartdate", map.get("lmEnddate"));
        	}  	
		}
        Map<String, Object> mapEnd = columnList.get(columnList.size()-1);
        mapEnd.put("lmStoptime", "23:59:59");
        mapEnd.put("lmEnddate", mapEnd.get("lmStartdate"));
        
        Map<String, Object> resultMap =new HashMap<>(); 
        resultMap.put("columnList", columnList);
        resultMap.put("startdateList", startdateList);
        
		return resultMap;
	}
	
	//获取文件流的编码格式
	public static String analysisCode(BufferedInputStream bufferin,int codeArrIndex){	
		try {		
			String[] codeArr = {"UTF-8","GBK","GB2312","ASCII","ISO-8859-1","UTF-16"};
			if(codeArr.length<=codeArrIndex){
				return "UTF-8";
			}	
			InputStreamReader reader = new InputStreamReader(bufferin, codeArr[codeArrIndex]);		
			bufferin.mark(0);
			
			BufferedReader bf = new BufferedReader(reader);			
		 
			String readLine = null;
			while((readLine = bf.readLine())!=null){
				if("".equals(readLine.trim())){
					continue;
				}			
				if(!verifyStr(readLine)){
					bufferin.reset();
					return analysisCode(bufferin, ++codeArrIndex);
				}			 
			}				
			bufferin.reset();
			return codeArr[codeArrIndex];			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "UTF-8";		
	}
	
	//校验字符串，正确true,乱码false
	private static boolean verifyStr(String str){		
		for (char c : str.toCharArray()) { 
			if((c>0x0080 && c<0x1FFF)||(c>0x20A0 && c<0x214F)||(c>0x2180 && c<0x245F)||
					(c>0x2500 && c<0x3000)||
					(c>0x301F && c<0x33FF)||
					(c>0xA000 && c<0xFF00) || (c>0xFF60)){
				if(c!='·'&&c!='℃'&&c!='の'&&c!='︰'){
					return false;
				}
			}
		}
		return true;		
	}
	 
}
