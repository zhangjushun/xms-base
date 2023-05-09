package com.xms.common.utils.util;

/**
 * Helper class for resolving placeholders in texts. Usually applied to file
 * paths.
 * 
 * <p>
 * A text may contain ${...} placeholders, to be resolved as system properties:
 * e.g. ${user.dir}.
 * 
 * @author chaojun xia
 */
public class SystemPropertyUtil {
	public static final String PLACEHOLDER_PREFIX = "${";

	public static final String PLACEHOLDER_SUFFIX = "}";

	/**
	 * Resolve ${...} placeholders in the given text, replacing them with
	 * corresponding system property values.
	 * 
	 * @param text
	 *            the String to resolve
	 * @return the resolved String
	 * @see #PLACEHOLDER_PREFIX
	 * @see #PLACEHOLDER_SUFFIX
	 */
	public static String resolvePlaceholders(String text) {
		StringBuffer buf = new StringBuffer(text);
		int startIndex = text.indexOf(PLACEHOLDER_PREFIX);
		while (startIndex != -1) {
			int endIndex = buf.toString().indexOf(PLACEHOLDER_SUFFIX,
					startIndex + PLACEHOLDER_PREFIX.length());
			if (endIndex != -1) {
				String placeholder = buf.substring(startIndex
						+ PLACEHOLDER_PREFIX.length(), endIndex);
				String propVal = System.getProperty(placeholder);
				if (propVal != null) {
					buf.replace(startIndex, endIndex
							+ PLACEHOLDER_SUFFIX.length(), propVal);
					startIndex = buf.toString().indexOf(PLACEHOLDER_PREFIX,
							startIndex + propVal.length());
				} else {
					startIndex = buf.toString().indexOf(PLACEHOLDER_PREFIX,
							endIndex + PLACEHOLDER_SUFFIX.length());
				}
			} else {
				startIndex = -1;
			}
		}
		return buf.toString();
	}
	
	public static boolean isWindows(){
		String os = System.getProperty("os.name").toLowerCase();
		boolean b = os.indexOf("window")!=-1?true:false;
		return b;
	}

	public static void main(String[] args) {
		System.setProperty("name", "windforce");
		String str = SystemPropertyUtil.resolvePlaceholders("${name}");
		System.out.println(str);
	}
}