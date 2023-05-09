package com.xms.common.utils.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CASMD5Util {
	
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(final byte[] bytes) {
        final StringBuilder buf = new StringBuilder(bytes.length * 2);

        for (int j = 0; j < bytes.length; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }
	
    /**利用MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     * @throws NoSuchAlgorithmException  没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException  
     */
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        md5.update(str.getBytes("utf-8"));
        return getFormattedText(md5.digest());
    }

    public static void main(String[] args) {
    	try {
    		System.out.println(CASMD5Util.EncoderByMd5("123456"));;
    		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
