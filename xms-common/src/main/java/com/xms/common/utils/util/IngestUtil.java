package com.xms.common.utils.util;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class IngestUtil {
	/**
	 * 下载文件到本地
	 * 
	 * @param urlString
	 *            被下载的文件地址
	 * @param filename
	 *            本地文件名
	 * @throws Exception
	 *             各种异常
	 */
	public static void download(String urlString, String fileName)
			throws Exception {
		URL url = new URL(urlString);
		URLConnection con = url.openConnection();
		con.setRequestProperty("Charset", "UTF-8");
		InputStream is = con.getInputStream();

		byte[] bs = new byte[1024];
		int len;
		OutputStream os = new FileOutputStream(fileName);

		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}

		os.close();
		is.close();
	}
	
	/**
	 * 下载文件到本地
	 * 
	 * @param urlString
	 *            被下载的文件地址
	 * @param filename
	 *            本地文件名
	 * @throws Exception
	 *             各种异常
	 */
	public static void download2(String urlString, String fileName) throws Exception {
		File file = null;
		try {
			// 统一资源
			URL url = new URL(urlString);
			// 连接类的父类，抽象类
			URLConnection urlConnection = url.openConnection();
			// http的连接类
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			// 设定请求的方法，默认是GET
			httpURLConnection.setRequestMethod("POST");
			// 设置字符编码
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			// 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）。
			httpURLConnection.connect();

			BufferedInputStream bin = new BufferedInputStream(httpURLConnection.getInputStream());

			file = new File(fileName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStream out = new FileOutputStream(file);
			int size = 0; 
			byte[] buf = new byte[1024];
			while ((size = bin.read(buf)) != -1) { 
				
				out.write(buf, 0, size);
			}
			bin.close();
			out.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
	
	public static void main(String[] args){		 
			try {
				IngestUtil.download2("https://img1.doubanio.com/img/celebrity/medium/1407766093.88.jpg","c:\\abcde.jpg");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
}
