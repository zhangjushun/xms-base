package com.xms.common.utils.util;

import java.util.UUID;

public class GUIDUtil {
	public static String get() {
		return get(true);
	}

	public static String get(boolean withoutBar) {
		String id = UUID.randomUUID().toString();
		if (withoutBar)
			id = id.replaceAll("-", "");
		return id;
	}

	public static void main(String[] args) {
		System.out.println(GUIDUtil.get().length());
	}
}