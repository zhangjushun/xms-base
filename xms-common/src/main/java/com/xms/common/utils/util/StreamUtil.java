package com.xms.common.utils.util;

import java.io.*;

public final class StreamUtil {
	public final static void write(InputStream is, OutputStream os)
			throws IOException {
		BufferedInputStream bis = new BufferedInputStream(is, 1024);
		int c = bis.read();
		while (c != -1) {
			os.write(c);
			c = bis.read();
		}
		// os.flush();
	}

	public final static String toString(InputStream is, String encoding)
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		write(is, bos);
		if (encoding == null) {
			return new String(bos.toByteArray());
		} else {
			return new String(bos.toByteArray(), encoding);
		}

	}

	public final static String toString(InputStream is) throws IOException {
		return toString(is, null);
	}

	public final static void closeQuitely(InputStream is) {
		try {
			if (is != null) {
				is.close();
			}
		} catch (IOException e) {
		}

	}

	public final static void closeQuitely(OutputStream os) {
		try {
			if (os != null) {
				os.close();
			}
		} catch (IOException e) {
		}
	}

	public final static void closeQuitely(InputStream is, OutputStream os) {
		closeQuitely(is);
		closeQuitely(os);
	}
}
