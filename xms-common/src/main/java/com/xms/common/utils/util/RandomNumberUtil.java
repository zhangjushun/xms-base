package com.xms.common.utils.util;

import java.util.Date;
import java.util.Random;

public class RandomNumberUtil {

	private static String getRandomNumber(int digCount) {
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(digCount);
		for (int i = 0; i < digCount; i++)
			sb.append((char) ('0' + rnd.nextInt(10)));
		return sb.toString();
	}

	public static String getRandomID(int num) {
		return String.valueOf(new Date().getTime()) + getRandomNumber(num);
	}
	

}
