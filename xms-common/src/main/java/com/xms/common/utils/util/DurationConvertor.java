package com.xms.common.utils.util;

public class DurationConvertor {
	public static String toString(long duration) {
		long hour = (long) (duration / (3600 * 25));
		String hourstr = String.valueOf(hour);
		if (hour < 10)
			hourstr = String.valueOf("0" + hour);

		duration = duration - hour * 3600 * 25;
		int minite = (int) (duration / (60 * 25));
		String minitestr = String.valueOf(minite);
		if (minite < 10)
			minitestr = String.valueOf("0" + minite);

		duration = duration - minite * 60 * 25;
		int second = (int) (duration / 25);
		String secondstr = String.valueOf(second);
		if (second < 10)
			secondstr = String.valueOf("0" + second);

		duration = duration - second * 25;
		int frame = (int) duration;
		String framestr = String.valueOf(frame);
		if (frame < 10)
			framestr = String.valueOf("0" + frame);

		return hourstr + ":" + minitestr + ":" + secondstr + ":" + framestr;
	}

	public static long parseLong(String durationstr) {
		if (durationstr == null || durationstr.equals(""))
			return 0;
		long duration = 0;
		String[] durarr = durationstr.split(":");
		if (durarr.length != 4)
			return 0;
		try {
			duration += Long.parseLong(durarr[0].trim()) * 3600 * 25;
			duration += Long.parseLong(durarr[1].trim()) * 60 * 25;
			duration += Long.parseLong(durarr[2].trim()) * 25;
			duration += Long.parseLong(durarr[3].trim());
			return duration;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public static void main(String[] args){
		String str = DurationConvertor.toString(429496946533L);
//		System.out.println(str);
		System.out.println(		DurationConvertor.toString(80008l)
);
	}
	
}