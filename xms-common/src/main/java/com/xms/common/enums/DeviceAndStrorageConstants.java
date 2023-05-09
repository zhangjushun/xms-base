package com.xms.common.enums;

/**
 * @Title:  DeviceConstants.java    
 * @Description: 存储常量类配置       
 * @author: xms     
 * @date:   2018年4月28日 上午10:34:23
 */
public class DeviceAndStrorageConstants {
	
	public static final int DEVICEID_HTTP = -1;
	
	public static final int DEVICETYPE_GENERAL = 0;
	public static final int DEVICETYPE_PREVIEW = 1;
	public static final int DEVICETYPE_TEMP = 2;
	
	public static final int DEVICEFLAG_ENABLED = 0;
	public static final int DEVICEFLAG_DISABLED = 1;
	
	public static final String DEVICE_ATTRIBUTE_LOCALDIR = "LOCALDIR";
	public static final String DEVICE_ATTRIBUTE_LINUXDIR = "LINUXDIR";
	public static final String DEVICE_ATTRIBUTE_UNCDIR = "UNCDIR";// UNC路径
	public static final String DEVICE_ATTRIBUTE_UNCLOGINNAME = "UNCLOGINNAME";// UNC用户名
	public static final String DEVICE_ATTRIBUTE_UNCLOGINPWD = "UNCLOGINPWD";// UNC密码

	
	public static final String DEVICE_ATTRIBUTE_HTTPURL = "HTTPURL";
	public static final String DEVICE_ATTRIBUTE_HTTPPORT = "HTTPPORT";
	public static final String DEVICE_ATTRIBUTE_HTTPURL_INTERN = "HTTPURL_INTERN";
	public static final String DEVICE_ATTRIBUTE_HTTPURL_OUTTER = "HTTPURL_OUTTER";
	
	public static final String DEVICE_ATTRIBUTE_FTPURL = "FTPURL";
	public static final String DEVICE_ATTRIBUTE_FTPLOGINNAME = "FTPLOGINNAME";
	public static final String DEVICE_ATTRIBUTE_FTPLOGINPWD = "FTPLOGINPWD";
	
	// TODO: 2020/3/2 下发使用的ftp地址、用户名、密码
	public static final String DEVICE_ATTRIBUTE_OUTPUTFTPURL = "OUTPUTFTPURL";
	public static final String DEVICE_ATTRIBUTE_OUTPUTFTPLOGINNAME = "OUTPUTFTPLOGINNAME";
	public static final String DEVICE_ATTRIBUTE_OUTPUTFTPLOGINPWD = "OUTPUTFTPLOGINPWD";
	
	
	public static final String DEVICE_ATTRIBUTE_BUCKETNAME = "BUCKETNAME";
	public static final String DEVICE_ATTRIBUTE_ROOTKEY = "ROOTKEY";
	
	//本地存储(文件存储)
	public static final int STORAGETYPE_LOCAL = 0;
	//云存储(对象存储)
	public static final int STORAGETYPE_CLOUD = 1;

}
