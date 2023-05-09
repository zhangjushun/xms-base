package com.xms.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
@Slf4j
public class DownloadUtils {
    public static void downLoad(String httpurl, String name,HttpServletResponse response) {
        BufferedInputStream bis = null;
        OutputStream os = null;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(httpurl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(20 * 1000);
            final ByteArrayOutputStream output = new ByteArrayOutputStream();
            IOUtils.copy(conn.getInputStream(), output);
            response.reset(); // 非常重要
            String fileName = new String(name.getBytes(), "ISO-8859-1");
            //文件名
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType("application/x-msdownload");
            byte[] buff = new byte[1024];
            try {
                os = response.getOutputStream();
                bis = new BufferedInputStream(new ByteArrayInputStream(output.toByteArray()));
                int i = 0;
                while ((i = bis.read(buff)) != -1) {
                    os.write(buff, 0, i);
                    os.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            log.error("getInputStreamByUrl 异常,exception is {}", e);
        } finally {

            try {
                if (os != null) {
                    os.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
            }
        }
    }
    public static byte[] httpDownload(String httpUrl) {
        // 1.下载网络文件
        int byteRead;
        URL url;
        try {
            url = new URL(httpUrl);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
            return null;
        }

        try {
            //2.获取链接
            URLConnection conn = url.openConnection();
            //3.输入流
            InputStream inStream = conn.getInputStream();
            //3.写入文件
            byte[] getData = readInputStream(inStream);
            inStream.close();
            return getData;
        }  catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
