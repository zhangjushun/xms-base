package com.xms.common.utils.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class ResponseUtil {
	public static final String default_charset = "gbk";

	public static void write(HttpServletResponse response, String result,
			String charset) {
		if (charset == null || charset.equals(""))
			charset = default_charset;
		try {
			response.setContentType("text/xml; charset=" + charset);
			response.setHeader("Cache-Control", "no-cache");
			response.flushBuffer();

			PrintWriter writer = response.getWriter();
			writer.write(result);
			writer.close();
		} catch (Exception e) {
		}
	}

	public static void write(HttpServletResponse response, String result) {
		write(response, result, null);
	}

}