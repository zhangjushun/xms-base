package com.xms.common.utils.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateCalculateUtil {
	
	/** 
     * 根据开始时间和结束时间返回时间段内的时间集合 
     *  
     * @param beginDate 
     * @param endDate 
     * @return List 
     */  
    public static List<Date> getDatesBetweenTwoDate(Date beginDate, Date endDate) {  
        List<Date> lDate = new ArrayList<Date>();  
        lDate.add(beginDate);// 把开始时间加入集合  
        
        if(beginDate.getTime()==endDate.getTime()){
        	return lDate;  
        }      
        
        Calendar cal = Calendar.getInstance();  
        // 使用给定的 Date 设置此 Calendar 的时间  
        cal.setTime(beginDate);  
        boolean bContinue = true;  
        while (bContinue) {  
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            cal.add(Calendar.DAY_OF_MONTH, 1);  
            // 测试此日期是否在指定日期之后  
            if (endDate.after(cal.getTime())) {  
                lDate.add(cal.getTime());  
            } else {  
                break;  
            }  
        }  
        lDate.add(endDate);// 把结束时间加入集合  
        return lDate;  
    }
    
    /**
     * 计算起止时间中每周的时间 ,比如nowDate是周三 那么返回beginDate和endDate 之间所有的周三
     * @param beginDate
     * @param endDate
     * @return
     */
    public static List<Date> getDayOfAllWeeks(Date endDate,Date weekDate){
    	
    	   List<Date> lDate = new ArrayList<Date>();  
           lDate.add(weekDate);// 把开始时间加入集合  
           Calendar cal=new GregorianCalendar();       
           // 使用给定的 Date 设置此 Calendar 的时间  
           cal.setTime(weekDate);  
           boolean bContinue = true;  
           while (bContinue) {  
               //往后增加七天  
               cal.add(Calendar.DATE,7);
               // 测试此日期是否在指定日期之后  
               if (!(endDate.before(cal.getTime()))) {  
                   lDate.add(cal.getTime());  
               } else {  
                   break;  
               }  
           }  
           return lDate;  
    }
    
    /**
     * 计算起止时间中每周的时间 ,比如weekDay是1,2，0 那么返回startDate和endDate 之间所有的周一、周二，周日
     * @param startDate
     * @param endDate
     * @param weekDay
     * @return
     */
    public static List<Date> getDayOfAllWeeks(Date startDate,Date endDate,String weekDay){		
		 List<String> weekDayList = Arrays.asList(weekDay.split(","));		 
		 List<Date> lDate = new ArrayList<Date>();  	 
	     Calendar cal = Calendar.getInstance();  
	     // 使用给定的 Date 设置此 Calendar 的时间  
	     cal.setTime(startDate);  
	     boolean bContinue = true; 
	     
	     while (bContinue) {
	    	
	         // 测试此日期是否在指定日期之后  
	         if (endDate.after(cal.getTime()) || endDate.equals(cal.getTime())) {  
	        	 String dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)-1+"";
	   			 if(weekDayList.contains(dayOfWeek)){
	   			 	lDate.add(cal.getTime());	   			 		
	   			 }
	   			 cal.add(Calendar.DAY_OF_MONTH, 1);   // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
	          } else {  
	              break;  
	          }  
	     } 	     
	     return lDate;  
	 }
	
    //比较两个日期大小
	public static Integer compareDate(String date1,String date2){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d1 = sdf.parse(date1);
			Date d2 = sdf.parse(date2);
			if(d1.getTime()>d2.getTime()){
				return 1;
			}else if(d1.getTime()==d2.getTime()){
				return 0;
			}else{
				return -1;
			}			
			
		} catch (ParseException e) {		
			e.printStackTrace();
		}
		return null;		
	}
	
	//获取第二天日期
	public static String nextDate(String date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long timcnt = sdf.parse(date).getTime()+24*3600*1000L;
			return sdf.format(new Date(timcnt));		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
}
